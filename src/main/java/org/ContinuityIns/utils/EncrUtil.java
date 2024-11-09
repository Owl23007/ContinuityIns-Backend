package org.ContinuityIns.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class EncrUtil {
    public static String getSalt(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getHash(String password, String salt) {
        try {
            // 创建MessageDigest实例
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // 将盐和密码组合
            String text = salt + password;
            // 计算哈希值
            byte[] hash = md.digest(text.getBytes());
            // 将哈希值编码为Base64字符串
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}