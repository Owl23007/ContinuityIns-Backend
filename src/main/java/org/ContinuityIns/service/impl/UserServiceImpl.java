package org.ContinuityIns.service.impl;

import org.ContinuityIns.entity.User;
import org.ContinuityIns.mapper.TokenMapper;
import org.ContinuityIns.mapper.UserMapper;
import org.ContinuityIns.entity.Result;
import org.ContinuityIns.entity.UserToken;
import org.ContinuityIns.entity.DTO.UserDTO;
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

    @Value("${org.ContinuityIns.servicePhone}")
    private String servicePhone;

    @Value("${org.ContinuityIns.serviceEmail}")
    private String serviceEmail;

    @Value("${org.ContinuityIns.workingHours}")
    private String workingHours;


    @Override
    public Result register(String username, String email, String password) {
        // 检查用户是否已存在
        UserDTO u = userMapper.getUserByUsername(username);
        if (u != null&&u.getStatus().equals(User.UserStatus.UNVERIFIED)) {
            sendActivationEmail(username, email, rootLink);
            return Result.error("用户已存在，请查看邮箱以激活账号");
        }
        if(u!=null&&u.getStatus().equals(User.UserStatus.NORMAL)){
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
        String subject = "欢迎注册ContinuityIns";
        String text = "欢迎注册ContinuityIns，点击链接激活账号：" + rootLink + "/user/active?email=" + email + "&token=" + token;
        emailService.sendEmail(email, subject, text);
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
        if (!Objects.equals(loginUser.getStatus(), User.UserStatus.NORMAL)) {
          switch (loginUser.getStatus()) {
              case User.UserStatus.UNVERIFIED:
                  return Result.error("账号未激活");
              case User.UserStatus.DEACTIVATED:
                  return Result.error("账号已注销");
              case User.UserStatus.BANNED:
                  return Result.error("账号已封禁");
          }
          return Result.error("账号状态异常");
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
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        String username = (String) map.get("username");
        UserDTO deleteUser = userMapper.getUserByUsername(username);
        String salt = userMapper.getSaltByUserId(deleteUser.getUserId());
        if (!Objects.equals(EncrUtil.getHash(password, salt), userMapper.getEncrPasswordByUserId(deleteUser.getUserId()))) {
            return Result.error("密码错误。");
        }
        userMapper.cancel(userId);
        userMapper.releaseUser(userId);
        if (!Objects.equals(deleteUser.getStatus(), "注销")) {
            return Result.error("注销失败。");
        }
        String token = UUID.randomUUID().toString();
        tokenService.insertToken(deleteUser.getUserId());
        String subject = "你的存续院账号已被注销";
        String text = "你的存续院账号已被注销，如非本人操作请于7天内联系管理员。\n账户ID:" + userId + "\n校验码:" + token + "\n联系邮箱:mailofowlwork@gmail.com\n请勿回复此邮件。";
        emailService.sendEmail(deleteUser.getEmail(), subject, text);
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
            UserToken existingToken = tokenMapper.getToken(userId);
            if (existingToken == null || currentTime - existingToken.getExpireTime() >= 24 * 60 * 60 * 1000) {
                // 生成新token
               tokenService.insertToken(userId);
            }
        } catch (Exception e) {
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
                            "<li>☎ 客服电话：%s</li>" +
                            "<li>✉ 服务邮箱：%s</li>" +
                            "<li>🕒 工作时间：%s</li>" +
                            "</ul>" +
                            "</div>" +

                            "<footer style='margin-top: 24px; color: #718096; font-size: 0.9em; text-align: center;'>" +
                            "<p>%s 安全团队</p>" +
                            "<p>%s</p>" +
                            "</footer>" +
                            "</div>",
                    companyName,
                    userName,
                    requestTime,
                    resetLink,
                    expireTime,
                    officialWebsite,
                    servicePhone,
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