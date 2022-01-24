package com.podo.pododev.backend.global.event;

import com.podo.pododev.core.util.DateTimeFormatUtil;
import com.podo.pododev.backend.global.context.ThreadLocalContext;
import com.podo.pododev.backend.global.external.email.EmailSender;
import com.podo.pododev.backend.global.util.HtmlDocumentUtil;
import com.podo.pododev.backend.global.util.ResourceFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class ReplyCommentNotifyListener {

    private final EmailSender emailSender;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void notifyReplyComment(ReplyCommentNotifyDto replyDto) throws IOException {
        ThreadLocalContext.debug("EVENT :: COMMENT REPLY NOTIFIER :: 댓글 답글 알림을 전송합니다");

        final String email = replyDto.getEmail();
        final String url = String.format("http://www.podo-dev.com/blogs/%s#comment", replyDto.getBlogId());
        final String username = replyDto.getUsername();
        final String writer = replyDto.getWriter();
        final String contents = replyDto.getContents();
        final LocalDateTime writeAt = replyDto.getWriteAt();
        final String originContents = replyDto.getOriginContents();
        final String originWriter = replyDto.getOriginWriter();
        final LocalDateTime originWriteAt = replyDto.getOriginWriteAt();

        String emailTitle = String.format("[podo-dev] '%s' 님께서 댓글의 답글을 작성하였습니다.", writer);
        String emailContents = ResourceFileUtil.readResourceFile("form/reply_comment_notify.html");
        emailContents = emailContents.replace("${contents}", HtmlDocumentUtil.line2br(contents));
        emailContents = emailContents.replace("${writer}", writer);
        emailContents = emailContents.replace("${writeAt}", DateTimeFormatUtil.dateTimeToBeautifulDate(writeAt));
        emailContents = emailContents.replace("${originContents}", HtmlDocumentUtil.line2br(originContents));
        emailContents = emailContents.replace("${originWriter}", originWriter);
        emailContents = emailContents.replace("${originWriteAt}", DateTimeFormatUtil.dateTimeToBeautifulDate(originWriteAt));
        emailContents = emailContents.replace("${url}", url);

        emailSender.sendHtmlEmail(username, email, emailTitle, emailContents);
    }




}