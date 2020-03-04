package com.podo.pododev.web.domain.blog.comment.api;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.response.*;
import com.podo.pododev.core.rest.response.dto.PageDto;
import com.podo.pododev.web.domain.blog.comment.CommentDto;
import com.podo.pododev.web.domain.blog.comment.application.CommentReadService;
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

        return CollectionResponse.ok(comments);
    }

    @GetMapping("/api/blogs/{blogId}/comments")
    public PageDto<CommentDto.response> paging(@PathVariable Long blogId, CommentDto.requestPaging requestPaging) {
        return commentReadService.paging(blogId, requestPaging);
    }

}
