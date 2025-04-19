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

    /**
     * 激活账号
     * @param email 邮箱
     * @param token 激活token
     * @return 激活结果
     */
    @GetMapping("/active")
    public Result<String> onActive(
            @RequestParam @Email(message = "邮箱格式不正确") String email,
            @RequestParam @NotNull(message = "激活token不能为空") String token) {
        return userService.activateAccount(email, token);
    }

    @PostMapping("/login")
    public Result<String> onLogin(@NotNull String identifier, @NotNull String password, HttpServletRequest request) {
        // 获取客户端IP地址
        String ipAddress = getClientIpAddress(request);
        return userService.login(identifier, password, ipAddress);
    }

    // 获取客户端真实IP地址
    private String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        
        // 如果是多级代理，取第一个IP地址
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(",")).trim();
        }
        return ipAddress;
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

    /**
     * 发送密码重置邮件
     * @param email 用户邮箱
     * @return 发送结果
     */
    @PostMapping("/sendResetEmail")
    public Result sendResetEmail(
            @RequestParam @Email(message = "邮箱格式不正确") String email) {
        return userService.sendResetEmail(email);
    }

    /**
     * 重置密码
     * @param email 用户邮箱
     * @param token 重置密码token
     * @param password 新密码
     * @return 重置结果
     */
    @PostMapping("/resetPassword")
    public Result resetPassword(
            @RequestParam @Email(message = "邮箱格式不正确") String email,
            @RequestParam @NotNull(message = "重置token不能为空") String token,
            @RequestParam @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{8,20}$", 
                message = "密码必须包含8-20位的字母、数字或特殊字符") String password) {
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
