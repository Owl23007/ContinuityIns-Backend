package org.ContinuityIns.service;

import org.ContinuityIns.DAO.MessageDAO;
import org.ContinuityIns.common.Result;

import java.util.List;

public interface MessageService {
    Result sendMessage(Integer toId, String content);

    Result<List<MessageDAO>> getMessage();

    Result<List<MessageDAO>> getUnreadMessage();
}
