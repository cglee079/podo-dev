package com.podo.pododev.backend.domain.blog.blog.api;

import com.podo.pododev.core.util.type.RequestHeader;
import com.podo.pododev.backend.domain.blog.blog.application.BlogInsertService;
import com.podo.pododev.backend.domain.blog.blog.application.BlogRemoveService;
import com.podo.pododev.backend.domain.blog.blog.application.BlogUpdateService;
import com.podo.pododev.backend.domain.blog.blog.dto.BlogInsert;
import com.podo.pododev.backend.domain.blog.blog.dto.BlogUpdate;
import com.podo.pododev.backend.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class BlogWriteApi {

    private final BlogInsertService blogInsertService;
    private final BlogUpdateService blogUpdateService;
    private final BlogRemoveService blogRemoveService;

    @PostMapping("/api/blogs")
    public void insert(@Valid @RequestBody BlogInsert insert) {
        blogInsertService.insertNewBlog(insert);
    }

    @PatchMapping("/api/blogs/{blogId}")
    public void update(@PathVariable Long blogId, @Valid @RequestBody BlogUpdate blogReq) {
        blogUpdateService.updateExistedBlogs(blogId, blogReq, LocalDateTime.now());
    }

    @DeleteMapping("/api/blogs/{blogId}")
    public void delete(@PathVariable Long blogId) {
        blogRemoveService.removeByBlogId(blogId);
    }

    @PostMapping("/api/blogs/{blogId}/hitCount")
    public void increaseHitCount(@PathVariable Long blogId, HttpServletResponse request) {
        blogUpdateService.increaseHitCount(blogId, request.getHeader(RequestHeader.USER_AGENT.value()), SecurityUtil.isAdmin());
    }
}
