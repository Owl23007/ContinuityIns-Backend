package org.ContinuityIns.service.impl;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.ContinuityIns.mapper.TokenMapper;
import org.ContinuityIns.mapper.UserMapper;
import org.ContinuityIns.common.Result;
import org.ContinuityIns.DTO.UserDTO;
import org.ContinuityIns.service.EmailService;
import org.ContinuityIns.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TokenMapper tokenMapper;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private TokenService tokenService;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        // 发送邮件
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Override
    public Result verifyRegisterEmail(String email, String token) {
        // 获取用户
        UserDTO u = userMapper.getUserByEmail(email);
        if (u == null) {
            return  Result.error("用户不存在或邮件已过期,请先注册");
        }
        if(u.getStatus().equals(UserDTO.UserStatus.NORMAL)){
            return Result.error("用户已激活，请勿重复激活");
        }


        // 验证token
        tokenService.verifyToken(u.getUserId(), token);

        // 更新用户状态
        userMapper.updateStatus( u.getUserId(), UserDTO.UserStatus.NORMAL);

        //初始化用户名和签名
        userMapper.init(u.getUserId(), "这个人很懒，什么都没有留下", "存续院用户"+u.getUserId());


        // 删除token
        tokenMapper.deleteToken(u.getUserId());

        return Result.success("激活成功");
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        helper.setFrom(from);

        mailSender.send(message);
    }
}