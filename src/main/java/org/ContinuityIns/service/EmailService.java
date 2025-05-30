package org.ContinuityIns.service;


import jakarta.mail.MessagingException;
import org.ContinuityIns.common.Result;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendEmail(String to, String subject, String text);

    Result verifyRegisterEmail(String email, String token);

    void sendHtmlEmail(String email, String format, String htmlContent) throws MessagingException;
}
