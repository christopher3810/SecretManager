package com.scm.module.Services;

import com.scm.module.Config.SMMConfig.EmailConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

//    private final JavaMailSender mailSender;
//    private final EmailConfiguration emailConfiguration;
//
//    @Autowired
//    public EmailService(JavaMailSender mailSender, EmailConfiguration emailConfiguration) {
//        this.mailSender = mailSender;
//        this.emailConfiguration = emailConfiguration;
//    }
//
//    public void sendActivationEmail(String email, String activationToken) {
//        String subject = "Account Activation";
//        String baseUrl = getBaseUrl();
//        String activationUrl = baseUrl + "/activate?token=" + activationToken;
//        String content = "Please click the following link to activate your account: " + activationUrl;
//
//        sendEmail(email, subject, content);
//    }
//
//    public void sendPasswordResetEmail(String email, String resetToken) {
//        String subject = "Password Reset";
//        String baseUrl = getBaseUrl();
//        String resetUrl = baseUrl + "/reset-password?token=" + resetToken;
//        String content = "Please click the following link to reset your password: " + resetUrl;
//
//        sendEmail(email, subject, content);
//    }

//    private void sendEmail(String recipient, String subject, String content) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(emailConfiguration.getFromAddress());
//        message.setTo(recipient);
//        message.setSubject(subject);
//        message.setText(content);
//
//        mailSender.send(message);
//    }

//    private String getBaseUrl() {
//        // Implement logic to retrieve the base URL from the application configuration or
//        // request context (if available). For example:
//        // return "https://your-application-url.com";
//        // Or, if using a dynamic base URL, you can pass it as a parameter to the method.
//        return emailConfiguration.getBaseUrl();
//    }
}
