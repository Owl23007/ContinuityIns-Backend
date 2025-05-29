package org.ContinuityIns.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserPO {
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

     public long getTokenExpirationToLong() {
        return tokenExpiration.toEpochSecond(java.time.ZoneOffset.UTC);
    }
}
