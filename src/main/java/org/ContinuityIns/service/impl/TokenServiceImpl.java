package org.ContinuityIns.service.impl;

import org.ContinuityIns.common.Result;
import org.ContinuityIns.mapper.UserMapper;
import org.ContinuityIns.DAO.UserDAO;
import org.ContinuityIns.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private UserMapper userMapper;

    // Token有效期
    private static final int TOKEN_VALIDITY_HOURS = 3*24;

    @Override
    public String generateToken(Integer userId) {
        // 检查用户是否存在
        if (userMapper.getUserById(userId) == null) {
            return null;
        }
        
        // 生成新的token
        String token = UUID.randomUUID().toString();
        
        // 计算过期时间(当前时间+24小时)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, TOKEN_VALIDITY_HOURS);
        Date expiryDate = calendar.getTime();
        
        // 更新用户token
        userMapper.updateToken(userId, token, expiryDate);
        
        return token;
    }

    public Boolean verifyToken(String email, String token) {
        // 根据token和类型获取用户
        UserDAO user = userMapper.getUserByEmail(email);
        if (user == null) {
            return false;
        }
        // 检查token是否匹配
        if (user.getToken() == null || !user.getToken().equals(token)) {
            return false;
        }
        // 检查token是否过期
        if (user.getTokenExpiration() != null && user.getTokenExpiration().isAfter(LocalDateTime.now())) {
            userMapper.updateToken(user.getUserId(), null, null);
            return true;
        }
        return false;
    }
}
