package org.ContinuityIns.service.impl;


import org.ContinuityIns.service.AiService;
import org.ContinuityIns.service.UserService;
import org.ContinuityIns.utils.AiClientUtil;
import org.ContinuityIns.utils.ThreadLocalUtil;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.util.*;

@Service
public class AiServiceImpl implements AiService {
    private final Map<Integer, Set<String>> userTasksMap = new HashMap<>();

    @Autowired
    private AiClientUtil aiClientUtil;

    @Autowired
    private UserService userService;


    @Override
    public String create(JsonNode message, String model) {

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        if(!userService.isVaildate(userId)){
            return null;
        }

        String taskId = aiClientUtil.initiateChat(message, model);
        if (userTasksMap.containsKey(userId)) {
            userTasksMap.get(userId).add(taskId);
        } else {
            Set<String> tasks = new HashSet<>();
            tasks.add(taskId);
            userTasksMap.put(userId, tasks);
        }
        return taskId;
    }

    //  getStream 方法
    @Override
    public Flux<String> getStream(String id) {
        return Flux.defer(() -> {
            Map<String, Object> map = ThreadLocalUtil.get();
            Integer userId = (Integer) map.get("id");

            // 使用RuntimeException替代特定异常
            if (!userService.isVaildate(userId)) {
                return Flux.error(new RuntimeException("用户未通过验证"));
            }

            Set<String> tasks = userTasksMap.getOrDefault(userId, Collections.emptySet());
            if (!tasks.contains(id)) {
                return Flux.error(new RuntimeException("无访问权限"));
            }

            return aiClientUtil.getChatStream(id)
                    .doFinally(signal -> {
                        if (signal == SignalType.CANCEL ||
                                signal == SignalType.ON_ERROR) {
                            stopChat(id);
                        }
                    });
        });
    }



    @Override
    public void stopChat(String id) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        if (userTasksMap.containsKey(userId)) {
            userTasksMap.get(userId).remove(id);
        }
        aiClientUtil.stopChat(id);
    }
}
