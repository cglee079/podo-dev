package com.podo.pododev.backend.domain.blog.comment.api;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.response.CollectionResponse;
import com.podo.pododev.core.rest.response.dto.PageDto;
import com.podo.pododev.backend.domain.blog.comment.application.CommentReadService;
import com.podo.pododev.backend.domain.blog.comment.dto.CommentRequestPaging;
import com.podo.pododev.backend.domain.blog.comment.dto.CommentResponse;
import com.podo.pododev.backend.domain.blog.comment.dto.CommentSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class CommentReadApi {

    private final CommentReadService commentReadService;

    @GetMapping("/api/comments/recent")
    public ApiResponse getRecentComments() {

        final List<CommentSummary> comments = commentReadService.getRecentComments();

        return CollectionResponse.ok(comments);
    }

    @GetMapping("/api/blogs/{blogId}/comments")
    public PageDto<CommentResponse> paging(@PathVariable Long blogId, CommentRequestPaging requestPaging) {
        return commentReadService.paging(blogId, requestPaging);
    }

}
