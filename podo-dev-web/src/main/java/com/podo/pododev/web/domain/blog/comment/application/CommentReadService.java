package com.podo.pododev.web.domain.blog.comment.application;

import com.podo.pododev.core.rest.response.dto.PageDto;
import com.podo.pododev.web.domain.blog.comment.Comment;
import com.podo.pododev.web.domain.blog.comment.CommentDto;
import com.podo.pododev.web.domain.blog.comment.repository.CommentRepository;
import com.podo.pododev.web.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CommentReadService {

    @Value("${blog.comment.page.size}")
    private Integer pageSize;

    @Value("${blog.comment.recent.size}")
    private Integer recentCommentSize;

    private final CommentRepository commentRepository;

    @Cacheable(value = "getRecentComments")
    public List<CommentDto.summary> getRecentComments() {
        final List<Comment> comments = commentRepository.findRecentComments(recentCommentSize);

        return comments.stream()
                .map(CommentDto.summary::new)
                .collect(toList());
    }

    @Cacheable(value = "getBlogComment", key = "#blogId + '#' + #requestPaging.page")
    public PageDto<CommentDto.response> paging(Long blogId, CommentDto.requestPaging requestPaging) {

        final int newPage = reversePage(requestPaging.getPage(), commentRepository.countByBlogId(blogId));

        final Pageable pageable = PageRequest.of(newPage, pageSize);
        final Page<Comment> comments = commentRepository.paging(blogId, pageable);

        final List<CommentDto.response> commentResponses = comments.stream()
                .map(comment -> new CommentDto.response(comment, SecurityUtil.getUserId()))
                .collect(toList());

        return PageDto.<CommentDto.response>builder()
                .contents(commentResponses)
                .currentPage(comments.getPageable().getPageNumber())
                .pageSize(comments.getPageable().getPageSize())
                .totalElements(comments.getTotalElements())
                .totalPages(comments.getTotalPages())
                .build();
    }

    private int reversePage(Integer requestPage, double commentCount) {
        double totalPage = Math.ceil((commentCount / (double) pageSize));

        if (totalPage == 0) {
            totalPage = 1;
        }

        return (int) (totalPage - requestPage - 1);
    }


}
