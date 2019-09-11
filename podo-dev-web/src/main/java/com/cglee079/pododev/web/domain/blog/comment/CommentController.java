package com.cglee079.pododev.web.domain.blog.comment;

import com.cglee079.pododev.core.global.response.*;
import com.cglee079.pododev.web.domain.blog.comment.aop.CommentNotice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 조회
     */
    @GetMapping("/api/blogs/{blogSeq}/comments")
    public ApiResponse paging(@PathVariable Long blogSeq, CommentDto.request request) {

        final PageDto<CommentDto.response> comments = commentService.paging(blogSeq, request);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .data(comments)
                .build();
    }


    /**
     * 댓글 작성
     */
    @CommentNotice
    @PostMapping("/api/blogs/{blogSeq}/comments")
    public ApiResponse insert(@PathVariable Long blogSeq, @Valid @RequestBody CommentDto.insert insert) {

        commentService.insert(blogSeq, insert);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/api/blogs/{blogSeq}/comments/{seq}")
    public ApiResponse delete(@PathVariable Long blogSeq, @PathVariable Long seq) {
        commentService.delete(seq);

        return StatusResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }


}
