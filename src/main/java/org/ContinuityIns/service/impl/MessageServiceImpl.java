package org.ContinuityIns.service.impl;

import org.ContinuityIns.common.Result;
import org.ContinuityIns.mapper.MessageMapper;
import org.ContinuityIns.service.MessageService;
import org.ContinuityIns.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public Result sendMessage(Integer toId, String content) {
        if (toId == null) return Result.error("接收者ID不能为空");
        if (content == null) return Result.error("消息内容不能为空");
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer fromId = (Integer) claims.get("id");
        int ok = messageMapper.sendMessage(fromId, toId, content);
        if (ok == 0) return Result.error("发送失败");
        return Result.success();
    }
}
