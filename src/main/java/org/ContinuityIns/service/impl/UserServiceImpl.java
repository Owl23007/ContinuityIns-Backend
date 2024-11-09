package org.ContinuityIns.service.impl;

import org.ContinuityIns.mapper.EmailTokenMapper;
import org.ContinuityIns.mapper.UserMapper;
import org.ContinuityIns.pojo.User;
import org.ContinuityIns.service.UserService;
import org.ContinuityIns.utils.ThreadLocalUtil;
import org.ContinuityIns.utils.EncrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmailTokenMapper emailTokenMapper;

    @Override
    public User getUserByUsername(String username) {
        // 获取用户对象
        return userMapper.getUserByUsername(username);
    }

    @Override
    public User getUserById(Integer userId) {
        // 以id获取其他用户对象
        return userMapper.getOtherUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        // 以邮箱获取用户对象
        return userMapper.getUserByEmail(email);
    }

    @Override
    public void register(String username, String email, String password) {
        // 生成盐
        String salt = EncrUtil.getSalt();
        //Hash加密
        String hashPassword = EncrUtil.getHash(password, salt);
        // 添加用户
        userMapper.add(username, email, hashPassword, salt, UUID.randomUUID().toString());
    }

    @Override
    public void updateInfo(String nickname, String signature) {
        // 获取用户
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer user_id = (Integer) map.get("id");
        User u = userMapper.getOtherUserById(user_id);
        // 更新用户基本信息
        u.setSignature(signature);
        u.setNickname(nickname);
        // 设置更新时间
        u.setUpdateTime(LocalDateTime.now());
        // 更新用户基本信息
        userMapper.update(u);
    }

    @Override
    public void updateAvatar(String url) {
        // 获取用户id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer user_id = (Integer) map.get("id");
        // 更新用户头像
        userMapper.updateAvatar(user_id, url);
    }

    @Override
    public void updatePassword(String newPwd) {
        // 获取盐
        String salt = EncrUtil.getSalt();
        // 加密密码
        String RSAPassword = EncrUtil.getHash(newPwd, salt);
        // 获取用户id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer user_id = (Integer) map.get("id");
        // 更新用户密码
        userMapper.updatePassword(user_id, RSAPassword, salt);
    }

    @Override
    public void deleteAcc(Integer userId) {
        // 注销用户
        userMapper.cancel(userId);
        // 释放用户
        userMapper.releaseUser(userId);
    }

    @Override
    public String getUsernameByEmail(String identifier) {
        // 以邮箱获取用户名
        return userMapper.getUsernameByEmail(identifier);
    }
}