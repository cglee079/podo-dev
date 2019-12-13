package com.podo.pododev.web.domain.blog.comment.api;

import com.podo.pododev.core.rest.response.*;
import com.podo.pododev.web.domain.blog.comment.CommentDto;
import com.podo.pododev.web.domain.blog.comment.service.CommentReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class CommentReadApi {

    private final CommentReadService commentReadService;

    @GetMapping("/api/comments/recent")
    public ApiResponse getRecentComments() {

        final List<CommentDto.summary> comments = commentReadService.getRecentComments();

        return ListResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(comments)
                .build();
    }

    @GetMapping("/api/blogs/{blogId}/comments")
    public ApiResponse paging(@PathVariable Long blogId, CommentDto.request request) {

        final PageDto<CommentDto.response> comments = commentReadService.paging(blogId, request);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(comments)
                .build();
    }


}
