package org.ContinuityIns.service;

public interface TokenService {
    void insertToken(Integer userId);

    Boolean verifyToken(Integer userId, String token);
}
