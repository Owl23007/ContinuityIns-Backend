package org.ContinuityIns.entity.DTO;

import lombok.Data;
import org.ContinuityIns.entity.User;

@Data
public class UserDTO {
    private Integer userId;
    private String username;
    private String nickname;
    private String signature;
    private String avatarImage;
    private User.UserStatus status;
    private String email;
}