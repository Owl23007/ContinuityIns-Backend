package org.ContinuityIns.service.impl;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.ContinuityIns.DTO.UserTokenDTO;
import org.ContinuityIns.mapper.TokenMapper;
import org.ContinuityIns.mapper.UserMapper;
import org.ContinuityIns.common.Result;
import org.ContinuityIns.DTO.UserDTO;
import org.ContinuityIns.service.EmailService;
import org.ContinuityIns.service.TokenService;
import org.ContinuityIns.service.UserService;
import org.ContinuityIns.utils.AliOssUtil;
import org.ContinuityIns.utils.EncrUtil;
import org.ContinuityIns.utils.JwtUtil;
import org.ContinuityIns.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TokenMapper tokenMapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AliOssUtil aliOssUtil;


    @Value("${org.ContinuityIns.url}")
    private String rootLink;

    @Value("${org.ContinuityIns.companyName}")
    private String companyName;

    @Value("${org.ContinuityIns.officialWebsite}")
    private String officialWebsite;

    @Value("${org.ContinuityIns.serviceEmail}")
    private String serviceEmail;

    @Value("${org.ContinuityIns.workingHours}")
    private String workingHours;


    @Override
    public Result register(String username, String email, String password) {
        // 检查用户是否已存在
        UserDTO u = userMapper.getUserByUsername(username);
        if (u != null&&u.getStatus().equals(UserDTO.UserStatus.UNVERIFIED)) {
            sendActivationEmail(username, email, rootLink);
            return Result.error("用户已存在，请查看邮箱以激活账号");
        }
        if(u!=null&&u.getStatus().equals(UserDTO.UserStatus.NORMAL)){
            return Result.error("用户已存在");
        }

        // 检查邮箱是否已经被注册
        UserDTO user = userMapper.getUserByEmail(email);
        if (user != null) {
            return Result.error("邮箱已被注册");
        }
        //是否为英文+数字组合
        if (!username.matches("^[a-zA-Z0-9]+$")) {
            return Result.error("用户名只能为英文和数字");
        }

        String salt = EncrUtil.getSalt();
        String hashPassword = EncrUtil.getHash(password, salt);
        userMapper.add(username, email, hashPassword, salt,username);
        // 发送验证邮件
        return sendActivationEmail(username, email, rootLink);
    }

    private Result sendActivationEmail(String username, String email, String rootLink) {
        UserDTO u = userMapper.getUserByUsername(username);
        if (u == null || u.getUserId() == null) {
            return Result.error("用户注册失败");
        }
        String token = UUID.randomUUID().toString();
        tokenService.insertToken(u.getUserId());

        String htmlContent= String.format(
                "<div style='font-family: Arial, sans-serif; line-height: 1.6; max-width: 600px; margin: 0 auto;'>" +
                        "<h3 style='color: #2B6CB0; border-bottom: 2px solid #2B6CB0; padding-bottom: 8px;'>欢迎注册%s</h3>" +
                        "<p>尊敬的新用户：</p>" +
                        "<p>感谢您注册%s！为了完成您的注册流程，请点击下方按钮激活您的账户：</p>" +
                        "<a href='%s/user/active?email=%s&token=%s' style='display: inline-block; padding: 12px 24px; background-color: #2B6CB0; color: white; text-decoration: none; border-radius: 4px; margin: 16px 0;'>立即激活账户</a>" +
                        "<div style='background: #F7FAFC; padding: 16px; border-left: 4px solid #2B6CB0; margin: 20px 0;'>" +
                        "<h4 style='margin-top: 0; color: #2C5282;'>安全提示：</h4>" +
                        "<ul style='margin: 0; padding-left: 20px; color: #4A5568;'>" +
                        "<li>链接有效期至：注册后24小时</li>" +
                        "<li>请确认浏览器地址栏显示：<code>%s</code></li>" +
                        "<li>不要将链接分享给任何人</li>" +
                        "</ul>" +
                        "</div>" +
                        "<div style='margin-top: 24px; padding: 16px; background: #EBF8FF; border-radius: 4px;'>" +
                        "<p style='margin: 0;'>需要帮助？请联系我们：</p>" +
                        "<ul style='margin: 8px 0 0 20px; padding-left: 0; list-style: none;'>" +
                        "<li>✉ 服务邮箱：%s</li>" +
                        "<li>🕒 工作时间：周一至周五 9:00-18:00</li>" +
                        "</ul>" +
                        "</div>" +
                        "<footer style='margin-top: 24px; color: #718096; font-size: 0.9em; text-align: center;'>" +
                        "<p>%s 团队</p>" +
                        "</footer>" +
                        "</div>",
                companyName, companyName, rootLink, email, token,rootLink,serviceEmail, companyName);
        try {
            emailService.sendHtmlEmail(
                    email,
                    String.format("欢迎!你正在注册%s的账号", companyName),
                    htmlContent
            );
        }catch (MessagingException e){
            throw new RuntimeException(e);
        }
        return Result.success("注册成功，请查看邮箱以激活账号");
    }

    @Override
    public Result<String> activateAccount(String email, String token) {
        emailService.verifyRegisterEmail(email, token);
        return Result.success("激活成功");
    }

    @Override
    public Result<String> login(String identifier, String password) {
        String username = identifier.contains("@") ? userMapper.getUsernameByEmail(identifier) : identifier;
        UserDTO loginUser = userMapper.getUserByUsername(username);
        if (loginUser == null) {
            return Result.error("用户名不存在");
        }
        if (!Objects.equals(loginUser.getStatus(), UserDTO.UserStatus.NORMAL)) {
            return switch (loginUser.getStatus()) {
                case UserDTO.UserStatus.UNVERIFIED -> Result.error("账号未激活");
                case UserDTO.UserStatus.DEACTIVATED -> Result.error("账号已注销");
                case UserDTO.UserStatus.BANNED -> Result.error("账号已封禁");
                default -> Result.error("账号状态异常");
            };
        }
        String salt = userMapper.getSaltByUserId(loginUser.getUserId());
        if (Objects.equals(EncrUtil.getHash(password, salt), userMapper.getEncrPasswordByUserId(loginUser.getUserId()))) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getUserId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @Override
    public Result<UserDTO> getUserInfo() {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        UserDTO u = userMapper.getUserByUsername(username);
        return Result.success(u);
    }

    @Override
    public Result getUserInfoById(Integer userId) {
        UserDTO u = userMapper.getUserById(userId);
        if (u == null) {
            return Result.error("用户不存在");
        }
        return Result.success(u);
    }

    @Override
    public Result updateUserInfo(String nickname, String signature) {
        if (!StringUtils.hasLength(nickname) || !StringUtils.hasLength(signature)) {
            return Result.error("缺少参数。");
        }
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        userMapper.update(userId, signature, nickname);
        return Result.success();
    }

    @Override
    public Result updateAvatar(String url) {
        if (!StringUtils.hasLength(url)) {
            return Result.error("URL不能为空。");
        }
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        userMapper.updateAvatar(userId, url);
        return Result.success();
    }

    @Override
    public Result updatePassword(Map<String, String> params) {
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少参数。");
        }
        Map<String, Object> claims = ThreadLocalUtil.get();
        UserDTO loginUser = userMapper.getUserByUsername((String) claims.get("username"));

        String salt = userMapper.getSaltByUserId(loginUser.getUserId());
        if (!EncrUtil.getHash(oldPwd, salt).equals(userMapper.getEncrPasswordByUserId(loginUser.getUserId()))) {
            return Result.error("原密码错误。");
        }
        if (!newPwd.equals(rePwd)) {
            return Result.error("两次密码不一致。");
        }

        Integer userId = (Integer) claims.get("id");
        userMapper.updatePassword(userId, EncrUtil.getHash(newPwd, salt), salt);
        return Result.success();
    }

    @Override
    public Result deleteAccount(String password) {
        // 获取用户信息
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        UserDTO deleteUser = userMapper.getUserById(userId);
        String salt = userMapper.getSaltByUserId(deleteUser.getUserId());

        // 检查密码是否正确
        if (!Objects.equals(EncrUtil.getHash(password, salt), userMapper.getEncrPasswordByUserId(deleteUser.getUserId()))) {
            return Result.error("密码错误。");
        }

        // 注销账户
        String username = deleteUser.getUsername();
        String email = deleteUser.getEmail();

        userMapper.cancel(userId);

        if (!Objects.equals(userMapper.getUserById(userId).getStatus(), UserDTO.UserStatus.DEACTIVATED)) {
            return Result.error("注销失败。");
        }

        //创建新token
        tokenService.insertToken(userId);
        String token = tokenMapper.getToken(userId).getToken();

        // 发送注销邮件
        String htmlContent = String.format(
                "<div style='font-family: Arial, sans-serif; line-height: 1.6; max-width: 600px; margin: 0 auto;'>" +
                        "<h3 style='color: #2B6CB0; border-bottom: 2px solid #2B6CB0; padding-bottom: 8px;'>您的%s账户已被注销</h3>" +
                        "<p>尊敬的%s：</p>" +
                        "<p>我们已收到您账户的注销请求，您的%s账号已被成功注销。</p>" +
                        "<p><strong>注意：</strong>如果这不是您的操作，请于7天内联系管理员进行恢复。</p>" +
                        "<div style='background: #F7FAFC; padding: 16px; border-left: 4px solid #2B6CB0; margin: 20px 0;'>" +
                        "<h4 style='margin-top: 0; color: #2C5282;'>账户信息：</h4>" +
                        "<ul style='margin: 0; padding-left: 20px; color: #4A5568;'>" +
                        "<li>账户ID: %s</li>" +
                        "<li>校验码: %s</li>" +
                        "</ul>" +
                        "</div>" +
                        "<div style='margin-top: 24px; padding: 16px; background: #EBF8FF; border-radius: 4px;'>" +
                        "<p style='margin: 0;'>需要帮助？请联系我们：</p>" +
                        "<ul style='margin: 8px 0 0 20px; padding-left: 0; list-style: none;'>" +
                        "<li>✉ 联系邮箱: mailofowlwork@gmail.com</li>" +
                        "</ul>" +
                        "</div>" +
                        "<footer style='margin-top: 24px; color: #718096; font-size: 0.9em; text-align: center;'>" +
                        "<p>%s 团队</p>" +
                        "</footer>" +
                        "</div>",
                companyName, username, companyName, userId, token, companyName);
        try {
            emailService.sendHtmlEmail(
                    email,
                    String.format("[重要] 您的%s账户已被注销", companyName),
                    htmlContent
            );
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return Result.success();
    }

    @Override
    public Result<Map<String, String>> getOssPolicy() {
        Map<String, String> policy = aliOssUtil.generatePolicy("avatars/", 2 * 1024 * 1024);
        return Result.success(policy);
    }

    @Override
    public Result sendResetEmail(String email) {
        // 获取用户信息
        UserDTO user = userMapper.getUserByEmail(email);
        if (user == null) {
            return Result.error("用户不存在");
        }

        final int userId = user.getUserId();
        final long currentTime = System.currentTimeMillis();

        try {
            // 检查token是否存在且未过期
            UserTokenDTO existingToken = tokenMapper.getToken(userId);

            if (existingToken == null) {
                // 生成新token
                tokenService.insertToken(userId);
            } else if (currentTime - existingToken.getCreateTimeToLong() > 3 * 24 * 60 * 60) {
                // token创建时间超过3天，删除旧token并生成新token
                tokenMapper.deleteToken(userId);
                tokenService.insertToken(userId);
            }else if (currentTime - existingToken.getCreateTimeToLong() <  60) {
                //60秒内只能发送一次
                return Result.error("60秒内只能发送一次重置邮件");
            }
        } catch (Exception e) {
            log.error("系统繁忙，请稍后重试", e);
            return Result.error("系统繁忙，请稍后重试");
        }
        String token = tokenMapper.getToken(userId).getToken();
        String userName = user.getUsername();
        // 构建邮件内容
        try {
            String resetLink = String.format("%s/resetPassword?email=%s&token=%s",
                    rootLink, URLEncoder.encode(email, StandardCharsets.UTF_8), token);

            // 日期格式化
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String requestTime = sdf.format(new Date(currentTime));
            String expireTime = sdf.format(new Date(currentTime + 24 * 60 * 60 * 1000));

            // HTML邮件模板
            String htmlContent = String.format(
                    "<div style='font-family: Arial, sans-serif; line-height: 1.6; max-width: 600px; margin: 0 auto;'>" +
                            "<h3 style='color: #2B6CB0; border-bottom: 2px solid #2B6CB0; padding-bottom: 8px;'>" +
                            "[重要] 您的%s账户密码重置指引" +
                            "</h3>" +

                            "<p>尊敬的%s：</p>" +

                            "<p>我们收到了您于<strong>%s</strong>发起的密码重置请求，请点击下方按钮完成操作：</p>" +

                            "<a href='%s' style='display: inline-block; padding: 12px 24px; background-color: #2B6CB0; " +
                            "color: white; text-decoration: none; border-radius: 4px; margin: 16px 0;'>" +
                            "立即重置密码" +
                            "</a>" +

                            "<div style='background: #F7FAFC; padding: 16px; border-left: 4px solid #2B6CB0; margin: 20px 0;'>" +
                            "<h4 style='margin-top: 0; color: #2C5282;'>安全提示：</h4>" +
                            "<ul style='margin: 0; padding-left: 20px; color: #4A5568;'>" +
                            "<li>链接有效期至：%s</li>" +
                            "<li>请确认浏览器地址栏显示：<code>%s</code></li>" +
                            "<li>不要将链接分享给任何人</li>" +
                            "</ul>" +
                            "</div>" +

                            "<div style='margin-top: 24px; padding: 16px; background: #EBF8FF; border-radius: 4px;'>" +
                            "<p style='margin: 0;'>需要帮助？请联系我们：</p>" +
                            "<ul style='margin: 8px 0 0 20px; padding-left: 0; list-style: none;'>" +
                            "<li>✉ 服务邮箱：%s</li>" +
                            "<li>🕒 工作时间：%s</li>" +
                            "</ul>" +
                            "</div>" +

                            "<footer style='margin-top: 24px; color: #718096; font-size: 0.9em; text-align: center;'>" +
                            "<p>%s 团队</p>" +
                            "<p>%s</p>" +
                            "</footer>" +
                            "</div>",
                    companyName,
                    userName,
                    requestTime,
                    resetLink,
                    expireTime,
                    officialWebsite,
                    serviceEmail,
                    workingHours,
                    companyName,
                    officialWebsite
            );

            // 发送HTML邮件
            emailService.sendHtmlEmail(
                    email,
                    String.format("[重要] 您的%s账户密码重置指引", companyName),
                    htmlContent
            );
            return Result.success("重置链接已发送至您的邮箱");
        } catch (Exception e) {
            return Result.error("邮件发送失败，请检查邮箱地址");
        }
    }

    @Override
    public Result resetPassword(String email, String token, String password) {
        emailService.verifyRegisterEmail(email, token);
        UserDTO u = userMapper.getUserByEmail(email);
        if (u == null) {
            return Result.error("用户不存在");
        }
        tokenMapper.deleteToken(u.getUserId());
        String salt = EncrUtil.getSalt();
        String hashPassword = EncrUtil.getHash(password, salt);
        userMapper.updatePassword(u.getUserId(), hashPassword, salt);
        return Result.success("重置密码成功");
    }
}
