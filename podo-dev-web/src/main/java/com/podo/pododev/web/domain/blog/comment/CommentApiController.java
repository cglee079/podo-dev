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


    /**
     * 댓글 조회
     */
    @GetMapping("/api/comments/recent")
    public ApiResponse getRecentComment() {

        final List<CommentDto.summary> comments = commentService.getRecentComments();

        return ListResponse.builder()
                .status(ApiStatus.SUCCESS)
                .results(comments)
                .build();
    }

    /**
     * 댓글 조회
     */
    @GetMapping("/api/blogs/{blogId}/comments")
    public ApiResponse paging(@PathVariable Long blogId, CommentDto.request request) {

        final PageDto<CommentDto.response> comments = commentService.paging(blogId, request);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(comments)
                .build();
    }


    /**
     * 댓글 작성
     */
    @CommentNotice
    @PostMapping("/api/blogs/{blogId}/comments")
    public ApiResponse insert(@PathVariable Long blogId, @Valid @RequestBody CommentDto.insert insert) {

        commentService.insert(blogId, insert);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/api/blogs/{blogId}/comments/{commentId}")
    public ApiResponse delete(@PathVariable Long blogId, @PathVariable Long commentId) {
        commentService.deleteByCommentId(commentId);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }


}
