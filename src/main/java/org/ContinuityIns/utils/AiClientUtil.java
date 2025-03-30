package org.ContinuityIns.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.Stream;

import static java.net.http.HttpRequest.BodyPublishers;

@Component
public class AiClientUtil {
    @Value("${aiApi.baseURL}")
    private String API_URL;

    @Value("${aiApi.apiId}")
    private String API_KEY;

    private final Map<String, TaskContext> activeTasks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(AiClientUtil.class);

    public AiClientUtil() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    private static class TaskContext {
        final Sinks.Many<String> sink;
        final CompletableFuture<HttpResponse<Stream<String>>> future;
        final ScheduledFuture<?> timeoutTask;
        volatile long lastActivityTime;

        TaskContext(Sinks.Many<String> sink, CompletableFuture<HttpResponse<Stream<String>>> future, ScheduledFuture<?> timeoutTask) {
            this.sink = sink;
            this.future = future;
            this.timeoutTask = timeoutTask;
            this.lastActivityTime = System.currentTimeMillis();
        }
    }

    public String initiateChat(JsonNode message, String model) {

        String taskId = UUID.randomUUID().toString();
        Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer(100, false);
        Map<String, Object> requestBodyMap = Map.of(
                "model", model,
                "messages", message,
                "stream", true,
                "stream_options", Map.of("include_usage", true)
        );

        HttpRequest request;
        try {
            String requestBody = objectMapper.writeValueAsString(requestBodyMap);
            request = buildRequest(requestBody);
        } catch (Exception e) {
            logger.error("构建请求失败", e);
            return null;
        }

        CompletableFuture<HttpResponse<Stream<String>>> future = client.sendAsync(request, HttpResponse.BodyHandlers.ofLines());

        ScheduledFuture<?> timeoutTask = scheduler.scheduleAtFixedRate(() -> {
            TaskContext context = activeTasks.get(taskId);
            if (context != null && System.currentTimeMillis() - context.lastActivityTime > 60000) {
                if (context.sink.tryEmitNext("data: [TIMEOUT]").isFailure()) {
                    // 如果发生错误，说明下游取消了订阅,检测任务是否已经被取消
                    if (!activeTasks.containsKey(taskId)) {
                        return;
                    }
                    logger.warn("未能为任务发出超时消息 {}", taskId);
                }
                context.sink.tryEmitComplete();
                cleanupTask(taskId);
            }
        }, 0, 5, TimeUnit.SECONDS);

        activeTasks.put(taskId, new TaskContext(sink, future, timeoutTask));

        future.thenAccept(response -> {
            if (response.statusCode() != 200) {
                logger.error("HTTP响应状态码错误: {}", response.statusCode());
                sink.tryEmitError(new RuntimeException("HTTP响应状态码错误: " + response.statusCode()));
                cleanupTask(taskId);
                return;
            }
            processResponseStream(taskId, sink, response);
        }).exceptionally(ex -> {
            logger.error("处理任务的响应流时出错{}", taskId, ex);
            sink.tryEmitError(ex);
            cleanupTask(taskId);
            return null;
        });

        return taskId;
    }

    public Flux<String> getChatStream(String taskId) {
        TaskContext context = activeTasks.get(taskId);
        if (context == null) {
            return Flux.error(new IllegalArgumentException("任务不存在"));
        }
        return context.sink.asFlux()
                .doOnCancel(() -> cleanupTask(taskId))
                .doOnTerminate(() -> cleanupTask(taskId));
    }

    public void stopChat(String taskId) {
        TaskContext context = activeTasks.remove(taskId);
        if (context != null) {
            context.sink.tryEmitComplete();
            context.future.cancel(true);
            context.timeoutTask.cancel(true);
        }
    }

    private HttpRequest buildRequest(String requestBody) {
        return HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .header("X-DashScope-SSE", "enable")
                .POST(BodyPublishers.ofString(requestBody))
                .build();
    }

    private void processResponseStream(String taskId, Sinks.Many<String> sink, HttpResponse<Stream<String>> response) {
        Stream<String> responseBody = response.body();
        Flux.fromStream(responseBody)
                .takeWhile(line -> activeTasks.containsKey(taskId))
                .doOnNext(line -> {
                    TaskContext context = activeTasks.get(taskId);
                    if (context == null) return; // 增加空检查

                    context.lastActivityTime = System.currentTimeMillis();

                    if (line.trim().equals("data: [DONE]")) {
                        if (!sink.tryEmitComplete().isSuccess()) {
                            cleanupTask(taskId); // 确保资源清理
                        }
                    } else {
                        if (sink.tryEmitNext(line).isFailure()) {
                            cleanupTask(taskId); // 立即清理失效任务
                            logger.warn("下游已取消订阅，清理任务 {}", taskId);
                        }
                    }
                })
                .doOnComplete(() -> {
                    sink.tryEmitComplete();
                    cleanupTask(taskId);
                })
                .doOnError(e -> {
                    if (e instanceof UncheckedIOException && e.getCause() != null) {
                        logger.warn("响应流已关闭 {}", taskId);
                    } else {
                        logger.error("在任务的响应流中出错 {}", taskId, e);
                    }
                    sink.tryEmitError(e);
                    cleanupTask(taskId);
                })
                .doFinally(signalType -> {
                    try {
                        responseBody.close();
                    } catch (Exception e) {
                        logger.error("关闭响应流时出错 {}", taskId, e);
                    }
                })
                .subscribe();
    }

    // 修改 cleanupTask 方法
    private void cleanupTask(String taskId) {
        TaskContext context = activeTasks.remove(taskId);
        if (context != null) {
            context.timeoutTask.cancel(true);
            context.future.cancel(true);
            try {
                context.sink.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
            } catch (Exception e) {
                logger.debug("流已完成无需清理");
            }
        }
    }

    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
}