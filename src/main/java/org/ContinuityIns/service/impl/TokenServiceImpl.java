package org.ContinuityIns.service.impl;

import org.ContinuityIns.mapper.TokenMapper;
import org.ContinuityIns.mapper.UserMapper;
import org.ContinuityIns.DTO.UserTokenDTO;
import org.ContinuityIns.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenMapper tokenMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void insertToken(Integer userId) {
        String token = UUID.randomUUID().toString();
        //判断用户是否存在
        if(userMapper.getUserById(userId) == null){
            return;
        }
        //判断是否已经存在token
        UserTokenDTO databaseToken = tokenMapper.getToken(userId);
        if (databaseToken != null) {
            tokenMapper.deleteToken(userId);
        }
        //插入token
        tokenMapper.insertToken(userId, token);
    }

    @Override
    public Boolean verifyToken(Integer userId, String token) {
        UserTokenDTO databaseToken = tokenMapper.getToken(userId);
        if (databaseToken == null) {
            return false;
        }
        if (databaseToken.getToken().equals(token)) {
            tokenMapper.deleteToken(userId);
            return true;
        }
        return false;
    }
}
