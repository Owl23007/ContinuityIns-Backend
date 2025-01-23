package org.ContinuityIns.service.impl;


import org.ContinuityIns.mapper.EmailTokenMapper;
import org.ContinuityIns.mapper.UserMapper;
import org.ContinuityIns.pojo.EmailToken;
import org.ContinuityIns.pojo.User;
import org.ContinuityIns.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailTokenMapper emailTokenMapper;

    @Autowired
    private UserMapper userMapper;

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
    public EmailToken getToken(String email) {
        return emailTokenMapper.getToken(userMapper.getUserByEmail(email).getUserId());
    }

    @Override
    public void verifyEmail(String email, String token) {
        // 获取用户
        User u = userMapper.getUserByEmail(email);

        if(u == null) {
            throw new RuntimeException("用户未注册或请求已过期,请重新注册");
        }
        if(u.getStatus().equals("正常")) {
            throw new RuntimeException("用户已激活,请勿重复激活");
        }

        // 获取token
        EmailToken emailToken = emailTokenMapper.getToken(u.getUserId());
        if (emailToken == null) {
            throw new RuntimeException("token不存在");
        }

        // 检查token是否正确
        if (!token.equals(emailToken.getToken())) {
            throw new RuntimeException("token错误");
        }

        // 更新用户状态
        userMapper.updateStatus( u.getUserId(),"正常");

        //初始化用户名和签名
        userMapper.init(u.getUserId(), u.getUsername(), "这个人很懒，什么都没有留下", "存续院用户"+u.getUserId());


        // 删除token
        emailTokenMapper.deleteToken(u.getUserId());
    }

    @Override
    public void insertToken(String email, String token) {
        int user_id = userMapper.getUserByEmail(email).getUserId();
        emailTokenMapper.insertToken(user_id, token);
    }

    @Override
    public void deleteToken(String email) {
        int user_id = userMapper.getUserByEmail(email).getUserId();
        emailTokenMapper.deleteToken(user_id);
    }
}