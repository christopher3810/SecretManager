package com.scm.module.Config.SMMConfig;

import java.util.Properties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ConfigurationProperties(prefix = "app.email")
@Getter
@Setter
public class EmailConfiguration {

    private String sender;
    private String baseUrl;
    private String mailHost;
    private int mailPort;
    private String mailUsername;
    private String mailPassword;
    private String mailSmtpAuth;
    private String mailSmtpStartTlsEnable;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);

        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", mailSmtpAuth);
        props.put("mail.smtp.starttls.enable", mailSmtpStartTlsEnable);
        props.put("mail.debug", "true");

        return mailSender;
    }

}