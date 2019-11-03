package com.cglee079.pododev.web.domain.blog.comment.aop;

import com.cglee079.pododev.web.domain.blog.BlogDto;
import com.cglee079.pododev.web.domain.blog.BlogService;
import com.cglee079.pododev.web.domain.blog.comment.CommentDto;
import com.cglee079.pododev.web.global.config.security.SecurityUtil;
import com.cglee079.pododev.web.global.infra.telegram.TelegramClient;
import com.cglee079.pododev.web.global.util.Formatter;
import com.cglee079.pododev.web.global.util.MarkdownUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Aspect
@RequiredArgsConstructor
@Component
public class CommentNotifier {

    private final TelegramClient telegramClient;
    private final BlogService blogService;
    private final String BLOG_ID_ARG_NAME = "blogId";

    @AfterReturning("@annotation(com.cglee079.pododev.web.domain.blog.comment.aop.CommentNotice)")
    public void checkRequestValidator(JoinPoint joinPoint) {
        CommentDto.insert comment = getCommentInsertDto(joinPoint);

        if (Objects.isNull(comment)) {
            //
            throw new RuntimeException();
        }

        final Long blogId = getBlogId(joinPoint);
        final String username = SecurityUtil.getUsername();
        final String contents = comment.getContents();
        final BlogDto.response blog = blogService.get(blogId);
        final StringBuilder message = new StringBuilder();

        message.append("#게시글에 댓글이 등록되었습니다.\n")
                .append("게시글 : ")
                .append(blog.getTitle())
                .append("\n")
                .append("이름 : ")
                .append(username)
                .append("\n")
                .append("시간 : ")
                .append(Formatter.dateTimeToDateTimeStr(LocalDateTime.now()))
                .append("\n")
                .append("내용 :\n")
                .append(MarkdownUtil.escape(contents));

        telegramClient.send(message.toString());

    }

    /**
     * PathVariable 에서 BlogId 가져옴
     *
     * @param joinPoint
     * @return
     */
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

    /**
     * RequestBody 에서 Insert 데이터 가져옴
     *
     * @param joinPoint
     * @return
     */
    private CommentDto.insert getCommentInsertDto(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        for (Object obj : args) {
            if (obj instanceof CommentDto.insert) {
                return (CommentDto.insert) obj;
            }
        }

        return null;
    }
}
