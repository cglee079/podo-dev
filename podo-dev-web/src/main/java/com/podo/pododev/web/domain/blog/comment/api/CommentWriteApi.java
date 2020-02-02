package com.podo.pododev.web.domain.blog.comment.api;

import com.podo.pododev.core.rest.response.*;
import com.podo.pododev.web.domain.blog.comment.CommentDto;
import com.podo.pododev.web.domain.blog.comment.aop.CommentNotice;
import com.podo.pododev.web.domain.blog.comment.service.CommentWriteService;
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

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }

    @DeleteMapping("/api/blogs/{blogId}/comments/{commentId}")
    public ApiResponse removeExistedCommentByCommentId(@PathVariable Long commentId) {
        commentWriteService.removeExistedCommentByCommentId(commentId);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }

}
