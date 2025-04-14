package org.ContinuityIns.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.ContinuityIns.common.Result;
import org.ContinuityIns.DAO.UserDAO;
import org.ContinuityIns.service.UserService;
import org.ContinuityIns.utils.CaptchaUtil;
import org.ContinuityIns.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result onRegister(@Pattern(regexp ="^[a-zA-Z0-9]{5,16}$")String username,
                             //5-16位英文数字组合
                             @Email String email,
                             @NotNull String password,
                             @NotNull String captchaId,
                             @NotNull String captchaCode) {
        boolean isValid = CaptchaUtil.verify(captchaCode, captchaId);
        if (!isValid) {
            return Result.error("验证码验证失败");
        }
        return userService.register(username, email, password);
    }

    @GetMapping("/active")
    public Result<String> onActive(@RequestParam String email, @RequestParam String token) {
        return userService.activateAccount(email, token);
    }

    @PostMapping("/login")
    public Result<String> onLogin(@NotNull String identifier, @NotNull String password) {
        return userService.login(identifier, password);
    }

    @GetMapping("/userinfo")
    public Result<UserDAO> onUserInfo() {
        return userService.getUserInfo();
    }

    @GetMapping("/userinfoById")
    public Result<UserDAO> onUserInfoById(@RequestParam Integer userId) {
        return userService.getUserInfoById(userId);
    }

    @PutMapping("/update")
    public Result onUpdate(@RequestParam String nickname, @RequestParam String signature) {
        return userService.updateUserInfo(nickname, signature);
    }

    @PatchMapping("/updateAvatar")
    public Result onUpdateAvatar(@RequestParam String url) {
        return userService.updateAvatar(url);
    }

    @PatchMapping("/updateBackground")
    public Result onUpdateBackground(@RequestParam String url) {
        return userService.updateBackground(url);
    }

    @PatchMapping("/updatePassword")
    public Result onUpdatePassword(@RequestBody Map<String, String> params) {
        return userService.updatePassword(params);
    }

    @PostMapping("/deleteAcc")
    public Result onDeleteAcc(@NotNull String password) {
        return userService.deleteAccount(password);
    }

    @GetMapping("/oss/policy")
    public Result<Map<String, Object>> onGetOssPolicy(@RequestParam @Pattern(regexp = "^(avatar|background|article)$") String type) {
        // 获取OSS上传策略
        return userService.getOssPolicy(type);
    }

    @PostMapping("/sendResetEmail")
    public Result sendResetEmail(@RequestParam String email) {
        return userService.sendResetEmail(email);
    }

    @PostMapping("/resetPassword")
    public Result resetPassword(@RequestParam String email,
                                @RequestParam String token,
                                @RequestParam String password) {
        return userService.resetPassword(email, token, password);
    }

    @PostMapping("/validateToken")
    public Result validateToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            return Result.error("Token为空");
        }
        token = token.substring(5);
        boolean isValid = JwtUtil.validateToken(token);
        if (isValid) {
            return Result.success("Token验证成功");
        } else {
            return Result.error("Token无效或已过期");
        }
    }

    @GetMapping("/{id}")
    public Result<UserDAO> getUserById(@PathVariable("id") Integer id) {
        return userService.getUserInfoById(id);
    }
}