package org.ContinuityIns.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ContinuityIns.common.Result;
import org.ContinuityIns.service.AiService;
import org.ContinuityIns.utils.AiClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class AiChatController {

    @Autowired
    private AiService aiService;

    @PostMapping("/chat")
    public Result<String> startChat(@RequestBody String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);

            JsonNode messagesNode = jsonNode.get("messages");
            if (messagesNode == null || !messagesNode.isArray()) {
                return Result.error("消息内容缺失或格式错误");
            }

            JsonNode messages = messagesNode;
            String taskId = aiService.create(messages);
            return Result.success(taskId);
        } catch (Exception e) {
            return Result.error("在解析消息时发生错误");
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