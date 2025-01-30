package org.ContinuityIns.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(unique = true, nullable = false)
    @NotNull(message = "用户名不能为空")
    private String username;

    @Column(nullable = false)
    @NotNull(message = "昵称不能为空")
    private String nickname;

    @Column(nullable = false)
    private String signature = "";

    @CreationTimestamp
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    private String avatarImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "状态不能为空")
    private UserStatus status;

    @Column(nullable = false)
    private String encrPassword;
    @Column(nullable = false)
    private String salt;

    @Email(message = "邮箱格式不正确")
    @Column(unique = true, nullable = false)
    private String email;

    public enum UserStatus {
        // 未验证，正常，封禁，注销
        UNVERIFIED, NORMAL, BANNED, DEACTIVATED
    }
}