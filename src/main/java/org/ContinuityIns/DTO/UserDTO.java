package org.ContinuityIns.DTO;

import lombok.Data;


@Data
public class UserDTO {
    private Integer userId;
    private String username;
    private String nickname;
    private String signature;
    private String avatarImage;
    private UserStatus status;
    private String email;

    public enum UserStatus {
        UNVERIFIED, NORMAL, BANNED, DEACTIVATED
    }
}