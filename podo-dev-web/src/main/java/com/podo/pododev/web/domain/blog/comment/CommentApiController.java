package com.podo.pododev.web.domain.blog.comment;

import com.podo.pododev.core.rest.response.*;
import com.podo.pododev.web.domain.blog.comment.aop.CommentNotice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class CommentApiController {

    private final CommentService commentService;

    @GetMapping("/api/comments/recent")
    public ApiResponse getRecentComments() {

        final List<CommentDto.summary> comments = commentService.getRecentComments();

        return ListResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(comments)
                .build();
    }

    @GetMapping("/api/blogs/{blogId}/comments")
    public ApiResponse paging(@PathVariable Long blogId, CommentDto.request request) {

        final PageDto<CommentDto.response> comments = commentService.paging(blogId, request);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(comments)
                .build();
    }

    @CommentNotice
    @PostMapping("/api/blogs/{blogId}/comments")
    public ApiResponse insertNewComment(@PathVariable Long blogId, @Valid @RequestBody CommentDto.insert commentInsert) {

        commentService.insertNewComment(blogId, commentInsert);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }

    @DeleteMapping("/api/blogs/{blogId}/comments/{commentId}")
    public ApiResponse removeCommentById(@PathVariable Long commentId) {
        commentService.removeExistedCommentByCommentId(commentId);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }


}
