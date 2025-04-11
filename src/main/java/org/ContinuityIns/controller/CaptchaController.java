package org.ContinuityIns.controller;

import org.ContinuityIns.common.Result;
import org.ContinuityIns.utils.CaptchaUtil;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class CaptchaController {
    @GetMapping("/captcha")
    public Result getCaptcha() {
        // 创建验证码
        String captcha= CaptchaUtil.createCaptchaBase64();

        // 将验证码信息返回给前端
        return Result.success(captcha);
    }

    @PostMapping("/verify-captcha")
    public Result verifyCaptcha(@RequestParam String code, @RequestParam String captchaId) {
        boolean isValid = CaptchaUtil.verify(code, captchaId);
        if (isValid) {
            return Result.success("验证码验证成功");
        } else {
            return Result.error("验证码验证失败");
        }
    }
}