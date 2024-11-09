package org.ContinuityIns.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class User {
    @NotNull
    @Getter
    private Integer userId;

    private String username;
    private String nickname;

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String email;

    @JsonIgnore
    private String encrPassword;

    private String signature;
    private String avatarImage;
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @JsonIgnore
    private String salt;
}