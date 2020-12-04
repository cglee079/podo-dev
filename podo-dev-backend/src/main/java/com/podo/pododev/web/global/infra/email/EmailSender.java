package com.podo.pododev.web.global.infra.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.activation.FileTypeMap;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailSender {

    @Value("${infra.gmail.admin.name}")
    private final String adminName;

    @Value("${infra.gmail.admin.email}")
    private final String adminEmail;

    private final ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(2);
    private final JavaMailSender mailSender;

    public void sendHtmlEmail(String username, String email, String title, String contents) {
        threadPoolExecutor.submit(() -> {
            log.debug("EMAIL :: '{}({})'로 메일을 발송합니다, 메일제목 : {}", email, username, title);
            try {
                mailSender.send(createMessage(username, email, title, contents));
            } catch (Exception e) {
                log.error("메일 전송에 문제가 발생하였습니다 {}", e.getMessage());
            }
        });
    }

    private MimeMessage createMessage(String username, String userEmail, String title, String contents) throws UnsupportedEncodingException, MessagingException {
        final MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage(), true, "utf-8");

        helper.setFrom(new InternetAddress(adminEmail, adminName));
        helper.setTo(new InternetAddress(userEmail, username));
        helper.setSubject(title);
        helper.setText(contents,  true);

        return helper.getMimeMessage();
    }
}
