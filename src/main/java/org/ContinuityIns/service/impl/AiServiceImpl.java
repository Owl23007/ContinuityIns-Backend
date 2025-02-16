package org.ContinuityIns.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.ContinuityIns.DTO.UserDTO;
import org.ContinuityIns.mapper.UserMapper;
import org.ContinuityIns.service.AiService;
import org.ContinuityIns.service.UserService;
import org.ContinuityIns.utils.AiClientUtil;
import org.ContinuityIns.utils.ThreadLocalUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class AiServiceImpl implements AiService {
    private final Map<Integer, Set<String>> userTasksMap = new HashMap<>();

    @Autowired
    private AiClientUtil aiClientUtil;

    @Autowired
    private UserService userService;

    @Override
    public String create(JsonNode message) {

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        if(!userService.isVaildate(userId)){
            return null;
        }

        String taskId = aiClientUtil.initiateChat(message);
        if (userTasksMap.containsKey(userId)) {
            userTasksMap.get(userId).add(taskId);
        } else {
            Set<String> tasks = new HashSet<>();
            tasks.add(taskId);
            userTasksMap.put(userId, tasks);
        }
        return taskId;
    }

    @Override
    public Flux<String> getStream(String id) {
        //权限校验
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        //是否验证
        if (!userService.isVaildate(userId)) {
            return Flux.error(new RuntimeException("用户未验证"));
        }
        //是否有权限
        if (!userTasksMap.containsKey(userId) || !userTasksMap.get(userId).contains(id)) {
            return Flux.error(new RuntimeException("无权限"));
        }
        return aiClientUtil.getChatStream(id);
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
