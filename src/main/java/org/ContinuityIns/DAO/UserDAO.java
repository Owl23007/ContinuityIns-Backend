package org.ContinuityIns.DAO;

import lombok.Data;

@Data
public class UserDAO {
    private Integer userId;
    private String username;
    private String nickname;
    private String signature;
    private String avatarImage;
    private String backgroundImage;
    private UserStatus status;
    private String email;

    public enum UserStatus {
        UNVERIFIED, NORMAL, BANNED, DEACTIVATED
    }
}