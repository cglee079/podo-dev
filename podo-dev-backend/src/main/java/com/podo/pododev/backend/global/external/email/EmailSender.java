package com.podo.pododev.backend.global.external.email;

import com.podo.pododev.backend.global.context.ThreadLocalContext;
import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger("EMAIL_LOGGER");

    @Value("${external.gmail.admin.name:}")
    private final String adminName;

    @Value("${external.gmail.admin.email:}")
    private final String adminEmail;

    private final ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(2);
    private final JavaMailSender mailSender;

    public void sendHtmlEmail(String username, String email, String title, String contents) {
        threadPoolExecutor.submit(() -> {
            ThreadLocalContext.init("send-email");
            try {
                ThreadLocalContext.putDateTime("send.startAt", LocalDateTime.now());
                ThreadLocalContext.put("receiver.email", email);
                ThreadLocalContext.put("receiver.username", username);
                ThreadLocalContext.put("receiver.title", title);
                ThreadLocalContext.put("receiver.contents", contents);
                ThreadLocalContext.debug(String.format("EMAIL :: '%s(%s)'로 메일을 발송합니다, 메일제목 : %s", email, username, title));
                mailSender.send(createMessage(username, email, title, contents));
            } catch (Exception e) {
                Sentry.captureException(e);
                ThreadLocalContext.putException(e);
            } finally {
                ThreadLocalContext.putDateTime("send.endAt", LocalDateTime.now());
                LOGGER.info("", StructuredArguments.value("context", ThreadLocalContext.toLog()));
                ThreadLocalContext.clear();
            }
        });
    }

    private MimeMessage createMessage(String username, String userEmail, String title, String contents) throws UnsupportedEncodingException, MessagingException {
        final MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage(), true, "utf-8");

        helper.setFrom(new InternetAddress(adminEmail, adminName));
        helper.setTo(new InternetAddress(userEmail, username));
        helper.setSubject(title);
        helper.setText(contents, true);

        return helper.getMimeMessage();
    }
}
