package com.podo.pododev.web.global.event.notifier;

import com.podo.pododev.core.util.DateTimeFormatUtil;
import com.podo.pododev.web.domain.blog.blog.application.BlogReadService;
import com.podo.pododev.web.domain.blog.comment.dto.CommentInsert;
import com.podo.pododev.web.global.config.aop.exception.CantFindArgumentException;
import com.podo.pododev.web.global.infra.telegram.TelegramClient;
import com.podo.pododev.web.global.util.HtmlDocumentUtil;
import com.podo.pododev.web.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class CommentNotifyListener {

    private final TelegramClient telegramClient;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void commentNotify(CommentNotifyDto commentNotifyDto) {
        log.debug("EVENT :: COMMENT NOTIFIER :: 댓글 알림을 전송합니다");

        final String message = new StringBuilder().append("#게시글에 댓글이 등록되었습니다.\n")
                .append("게시글 : ")
                .append(commentNotifyDto.getBlogTitle())
                .append("\n")
                .append("이름 : ")
                .append(commentNotifyDto.getUsername())
                .append("\n")
                .append("시간 : ")
                .append(DateTimeFormatUtil.dateTimeToDateTimeStr(commentNotifyDto.getWriteAt()))
                .append("\n")
                .append("내용 :\n")
                .append(HtmlDocumentUtil.escapeHtml(commentNotifyDto.getContent()))
                .toString();

        telegramClient.notifyAdmin(message);
    }

}
