package org.ContinuityIns.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
public class UserDAO {
    private Integer userId;
    private String username;
    private String nickname;
    private String signature;
    private String avatarImage;
    private String backgroundImage;
    private String email;
    private UserStatus status;
    private LocalDateTime lastLogin;

    @JsonIgnore
    private String encrPassword;
    @JsonIgnore
    private String salt;
    @JsonIgnore
    private String token;
    @JsonIgnore
    private LocalDateTime tokenExpiration;
    @JsonIgnore
    private String lastLoginIp;
    @JsonIgnore
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    
    // 用户设置相关字段
    @JsonIgnore
    private UserTheme theme;
    @JsonIgnore
    private String notificationPreferences;
    @JsonIgnore
    private String privacySettings;

    public enum UserStatus {
        UNVERIFIED, NORMAL, BANNED, DEACTIVATED
    }
    
    public enum UserTheme {
        LIGHT, DARK, SYSTEM
    }

    @JsonIgnore
    public long getTokenExpirationToLong() {
        if (tokenExpiration == null) {
            return 0;
        } else {
            return tokenExpiration.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        }


    }
}
