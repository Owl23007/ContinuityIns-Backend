package org.ContinuityIns.utils;

import com.wf.captcha.GifCaptcha;

import com.wf.captcha.base.Captcha;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class CaptchaUtil {
    private static final ConcurrentHashMap<String, String> captchaMap = new ConcurrentHashMap<>();

    public static String createCaptchaBase64() {
        Captcha captcha = new GifCaptcha(100, 40, 4);
        captcha.setCharType(Captcha.TYPE_DEFAULT);

        String captchaId = UUID.randomUUID().toString();
        captchaMap.put(captchaId, captcha.text());

        // 设置验证码过期时间
        captchaMap.computeIfPresent(captchaId, (key, value) -> {
            new Thread(() -> {
                try {
                    TimeUnit.MINUTES.sleep(5);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                captchaMap.remove(key);
            }).start();
            return value;
        });

        // 将验证码图片转换为 Base64 字符串
        String base64Image = captcha.toBase64();


        return captchaId + ":" + base64Image;
    }

    public static boolean verify(String code, String captchaId) {
        String storedCode = captchaMap.get(captchaId);
        if (storedCode == null) {
            return false;
        }
        boolean isMatch = storedCode.equalsIgnoreCase(code);
        if (isMatch) {
            captchaMap.remove(captchaId);
        }
        return isMatch;
    }
}