package org.ContinuityIns.service;


import org.ContinuityIns.pojo.EmailToken;

public interface EmailService {
    EmailToken getToken(String email);

    void sendEmail(String to, String subject, String text);

    void verifyEmail(String email, String token);

    void insertToken(String email, String token);

    void deleteToken(String email);
}
