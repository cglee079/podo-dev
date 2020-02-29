package com.podo.pododev.web.domain.blog.comment.api;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.response.*;
import com.podo.pododev.web.domain.blog.comment.CommentDto;
import com.podo.pododev.web.global.config.aop.notifier.CommentNotice;
import com.podo.pododev.web.domain.blog.comment.application.CommentWriteService;
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
    public ApiResponse insertNewComment(@PathVariable Long blogId, @Valid @RequestBody CommentDto.insert commentInsert) {

        commentWriteService.insertNewComment(blogId, commentInsert);

        return StatusResponse.success();
    }

    @DeleteMapping("/api/blogs/{blogId}/comments/{commentId}")
    public ApiResponse removeExistedCommentByCommentId(@PathVariable Long commentId) {
        commentWriteService.removeExistedCommentByCommentId(commentId);

        return StatusResponse.success();
    }

}
