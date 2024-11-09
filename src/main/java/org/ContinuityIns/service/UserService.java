package org.ContinuityIns.service;

import jakarta.validation.constraints.Pattern;
import org.ContinuityIns.pojo.User;

public interface UserService {
    User getUserByUsername(String username);

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    void register(String username, String email, String password);

    void updateInfo(String nickname, String signature);

    void updateAvatar(String url);

    void updatePassword(String newPwd);

    void deleteAcc(Integer userId);

    String getUsernameByEmail(@Pattern(regexp = "^\\S{5,16}$") String identifier);
}
