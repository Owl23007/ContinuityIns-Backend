package org.ContinuityIns.service;

import org.ContinuityIns.po.MessagePO;
import org.ContinuityIns.common.Result;

import java.util.List;

public interface MessageService {
    Result sendMessage(Integer toId, String content);

    Result<List<MessagePO>> getMessage();

    Result<List<MessagePO>> getUnreadMessage();
}
