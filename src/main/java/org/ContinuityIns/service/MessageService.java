package org.ContinuityIns.service;

import org.ContinuityIns.common.Result;

public interface MessageService {
    Result sendMessage(Integer toId, String content);
}
