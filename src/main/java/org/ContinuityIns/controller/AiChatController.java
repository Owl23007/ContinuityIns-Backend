package org.ContinuityIns.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ContinuityIns.common.Result;
import org.ContinuityIns.service.AiService;
import org.ContinuityIns.utils.AiClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Set;

@RestController
@RequestMapping("/ai")
public class AiChatController {
    private static final Set<String> SUPPORTED_MODELS = Set.of(
            "deepseek-v3",
            "deepseek-r1",
            "qwq-plus",
            "qwen-max-2025-01-25"
    );

    @Autowired
    private AiService aiService;

    // 修改后的startChat方法（关键修改点说明）
    @PostMapping("/chat")
    public Result<String> startChat(@RequestBody String requestBody) { // 改为接收完整请求体
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(requestBody); // 解析完整请求体

            // 提取messages和model节点
            JsonNode messagesNode = rootNode.get("messages");
            JsonNode modelNode = rootNode.get("model");

            // 验证必要字段存在性
            if (messagesNode == null || !messagesNode.isArray()) {
                return Result.error("消息内容缺失或格式错误");
            }
            if (modelNode == null || modelNode.isNull()) {
                return Result.error("模型参数缺失");
            }

            // 获取模型参数值
            String model = modelNode.asText();
            if (!SUPPORTED_MODELS.contains(model)) {
                return Result.error("不支持的模型类型: " + model);
            }

            // 调用服务层
            String taskId = aiService.create(messagesNode, model);
            return Result.success(taskId);
        } catch (JsonProcessingException e) {
            return Result.error("JSON解析失败: " + e.getMessage());
        } catch (Exception e) {
            return Result.error("请求处理异常: " + e.getClass().getSimpleName());
        }
    }


    @GetMapping(value = "/{taskId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(@PathVariable String taskId) {
        return aiService.getStream(taskId);
    }

    @DeleteMapping("/{taskId}")
    public Result stopChat(@PathVariable String taskId) {
        aiService.stopChat(taskId);
        return Result.success();
    }

}