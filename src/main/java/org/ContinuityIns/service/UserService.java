package org.ContinuityIns.service;

import org.ContinuityIns.DAO.UserDAO;
import org.ContinuityIns.common.Result;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    Result register(String username, String email, String password);

    Result<String> activateAccount(String email, String token);

    Result<String> login(String identifier, String password);

    Result<UserDAO> getUserInfo();

    Result getUserInfoById(Integer userId);

    Result updateUserInfo(String nickname, String signature);

    Result updateAvatar(String url);

    Result updateBackground(String url);

    Result updatePassword(Map<String, String> params);

    Result deleteAccount(String password);

    Result<Map<String, Object>> getOssPolicy(String type);

    Result sendResetEmail(String email);

    Result resetPassword(String email, String token, String password);

    Boolean isVaildate(Integer userId);
}