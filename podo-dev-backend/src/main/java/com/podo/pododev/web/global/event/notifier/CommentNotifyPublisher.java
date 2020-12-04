package com.podo.pododev.web.global.event.notifier;

import com.podo.pododev.core.util.DateTimeFormatUtil;
import com.podo.pododev.web.domain.blog.blog.application.BlogReadService;
import com.podo.pododev.web.domain.blog.comment.model.Comment;
import com.podo.pododev.web.global.infra.telegram.TelegramClient;
import com.podo.pododev.web.global.util.HtmlDocumentUtil;
import com.podo.pododev.web.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class CommentNotifyPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishCommentNotify(CommentNotifyDto commentNotifyDto){
        applicationEventPublisher.publishEvent(commentNotifyDto);
    }
}