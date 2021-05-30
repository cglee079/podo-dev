package com.podo.pododev.backend.global.external.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {

    @Value("${external.gmail.send.timeout:3000}")
    private final Integer sendTimeout;

    @Value("${external.gmail.admin.id:}")
    private final String id;

    @Value("${external.gmail.admin.password:}")
    private final String password;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(id);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.connectiontimeout", sendTimeout);
        props.put("mail.smtp.timeout", sendTimeout);
        props.put("mail.smtp.auth", true);

        return mailSender;
    }
}
