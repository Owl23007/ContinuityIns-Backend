package org.ContinuityIns.controller;

import jakarta.websocket.OnError;
import org.ContinuityIns.entity.Result;
import org.ContinuityIns.entity.DTO.UserDTO;
import org.ContinuityIns.service.UserService;
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
                             @NotNull String password) {
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
    public Result<UserDTO> onUserInfo() {
        return userService.getUserInfo();
    }

    @GetMapping("/userinfoById")
    public Result<UserDTO> onUserInfoById(@RequestParam Integer userId) {
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

    @PatchMapping("/updatePassword")
    public Result onUpdatePassword(@RequestBody Map<String, String> params) {
        return userService.updatePassword(params);
    }

    @PostMapping("/deleteAcc")
    public Result onDeleteAcc(@NotNull String password) {
        return userService.deleteAccount(password);
    }

    @GetMapping("/oss/policy")
    public Result<Map<String, String>> onGetOssPolicy() {
        return userService.getOssPolicy();
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
}