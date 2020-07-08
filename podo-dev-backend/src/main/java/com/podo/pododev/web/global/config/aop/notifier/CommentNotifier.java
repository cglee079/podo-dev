package com.podo.pododev.web.global.config.aop.notifier;

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
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
public class CommentNotifier {

    private final TelegramClient telegramClient;
    private final BlogReadService blogReadService;
    private final String BLOG_ID_ARG_NAME = "blogId";

    @AfterReturning("@annotation(com.podo.pododev.web.global.config.aop.notifier.CommentNotice)")
    public void commentNotify(JoinPoint joinPoint) {
        log.debug("AOP :: COMMENT NOTIFIER :: 댓글 알림을 전송합니다");

        final CommentInsert comment = getCommentInsert(joinPoint);

        if (Objects.isNull(comment)) {
            throw new CantFindArgumentException(CommentInsert.class);
        }

        if (SecurityUtil.isAdmin()) {
            return;
        }

        final Long blogId = getBlogId(joinPoint);
        final String username = SecurityUtil.getUsername();
        final String contents = comment.getContents();
        final String blogTitle = blogReadService.getBlogTitleById(blogId);
        final StringBuilder message = new StringBuilder();

        message.append("#게시글에 댓글이 등록되었습니다.\n")
                .append("게시글 : ")
                .append(blogTitle)
                .append("\n")
                .append("이름 : ")
                .append(username)
                .append("\n")
                .append("시간 : ")
                .append(DateTimeFormatUtil.dateTimeToDateTimeStr(LocalDateTime.now()))
                .append("\n")
                .append("내용 :\n")
                .append(HtmlDocumentUtil.escapeHtml(contents));

        telegramClient.notifyAdmin(message.toString());
    }

    private Long getBlogId(JoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();

        String[] params = codeSignature.getParameterNames();
        for (int i = 0; i < params.length; i++) {
            if (params[i].equals(BLOG_ID_ARG_NAME)) {
                return (Long) joinPoint.getArgs()[i];
            }
        }

        return null;
    }

    private CommentInsert getCommentInsert(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        for (Object obj : args) {
            if (obj instanceof CommentInsert) {
                return (CommentInsert) obj;
            }
        }

        return null;
    }
}
