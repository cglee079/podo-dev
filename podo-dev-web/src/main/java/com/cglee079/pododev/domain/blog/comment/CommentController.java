package com.cglee079.pododev.domain.blog.comment;

import com.cglee079.pododev.domain.blog.comment.aop.CommentNotice;
import com.cglee079.pododev.global.response.ApiResponse;
import com.cglee079.pododev.global.response.DataResponse;
import com.cglee079.pododev.global.response.ResponseStatus;
import com.cglee079.pododev.global.response.StatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 조회
     */
    @GetMapping("/blogs/{blogSeq}/comments")
    public ApiResponse list(@PathVariable Long blogSeq) {

        List<CommentDto.response> comments = commentService.list(blogSeq);

        return DataResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .data(comments)
                .build();
    }


    /**
     * 댓글 작성
     */
    @CommentNotice
    @PostMapping("/blogs/{blogSeq}/comments")
    public ApiResponse insert(@PathVariable Long blogSeq, @Valid @RequestBody CommentDto.insert insert) {

        commentService.insert(blogSeq, insert);

        return StatusResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .build();
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/blogs/{blogSeq}/comments/{seq}")
    public ApiResponse delete(@PathVariable Long blogSeq, @PathVariable Long seq) {
        commentService.delete(seq);

        return StatusResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .build();
    }


}
