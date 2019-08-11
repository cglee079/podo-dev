package com.cglee079.pododev.web.domain.blog.comment.aop;

import com.cglee079.pododev.web.domain.blog.BlogDto;
import com.cglee079.pododev.web.domain.blog.BlogService;
import com.cglee079.pododev.web.domain.blog.comment.CommentDto;
import com.cglee079.pododev.web.global.infra.telegram.TelegramClient;
import com.cglee079.pododev.web.global.util.Formatter;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@Aspect
public class CommentNoticeSender {

    private final TelegramClient telegramClient;
    private final BlogService blogService;
    private final String ARG_NAME_BLOG_SEQ = "blogSeq";

    @After("@annotation(com.cglee079.pododev.web.domain.blog.comment.aop.CommentNotice)")
    public void checkRequestValidator(JoinPoint joinPoint) {
        CommentDto.insert comment = getCommentInsert(joinPoint);

        Long blogSeq = getBlogSeq(joinPoint);
        String username = comment.getUsername();
        String contents = comment.getContents();
        BlogDto.response blog = blogService.get(blogSeq);

        StringBuilder message = new StringBuilder();

        message.append("#블로그에 댓글이 등록되었습니다.\n");
        message.append("블로그	 : " + blog.getTitle() + "\n");
        message.append("이름 : " + username + "\n");
        message.append("시간 : " + Formatter.dateTimeToStr(LocalDateTime.now()) + "\n");
        message.append("내용 :\n");
        message.append(contents);

        telegramClient.send(message.toString());

    }

    private Long getBlogSeq(JoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();

        String[] params = codeSignature.getParameterNames();
        for (int i = 0; i < params.length; i++) {
            if (params[i].equals(ARG_NAME_BLOG_SEQ)) {
                return (Long) joinPoint.getArgs()[i];
            }
        }

        return null;
    }

    private CommentDto.insert getCommentInsert(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        for (Object obj : args) {
            if (obj instanceof CommentDto.insert) {
                return (CommentDto.insert) obj;
            }
        }
        return null;
    }
}
