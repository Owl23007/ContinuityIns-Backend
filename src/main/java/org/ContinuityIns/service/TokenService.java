package org.ContinuityIns.service;

import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    void insertToken(Integer userId);

    Boolean verifyToken(Integer userId, String token);
}
