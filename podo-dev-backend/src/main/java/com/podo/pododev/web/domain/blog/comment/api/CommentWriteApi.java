package com.podo.pododev.web.domain.blog.comment.api;

import com.podo.pododev.web.domain.blog.comment.application.CommentWriteService;
import com.podo.pododev.web.domain.blog.comment.dto.CommentInsert;
import com.podo.pododev.web.global.config.aop.notifier.CommentNotice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class CommentWriteApi {

    private final CommentWriteService commentWriteService;

    @CommentNotice
    @PostMapping("/api/blogs/{blogId}/comments")
    public void insertNewComment(@PathVariable Long blogId, @Valid @RequestBody CommentInsert commentInsert) {
        commentWriteService.insertNewComment(blogId, commentInsert);
    }

    @DeleteMapping("/api/blogs/{blogId}/comments/{commentId}")
    public void removeByCommentId(@PathVariable Long commentId) {
        commentWriteService.removeByCommentId(commentId);
    }

}
