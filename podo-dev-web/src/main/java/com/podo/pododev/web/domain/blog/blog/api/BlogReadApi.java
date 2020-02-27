package com.podo.pododev.web.domain.blog.blog.api;

import com.podo.pododev.core.rest.response.*;
import com.podo.pododev.web.domain.blog.blog.BlogDto;
import com.podo.pododev.web.domain.blog.blog.application.BlogReadService;
import com.podo.pododev.web.global.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class BlogReadApi {

    private final BlogReadService blogReadService;

    @GetMapping("/api/blogs/archive")
    public ApiResponse getArchive() {

        final Map<Integer, List<BlogDto.archive>> archive = blogReadService.getArchiveMapByYearOfPublishAt(SecurityUtil.isAdmin());

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(archive)
                .build();
    }

    @GetMapping("/api/blogs/{blogId}")
    public ApiResponse findByBlogId(@PathVariable Long blogId) {

        final BlogDto.response blog = blogReadService.getExistedBlogByBlogId(blogId);

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(blog)
                .build();
    }

    @GetMapping("/api/blogs")
    public ApiResponse paging(BlogDto.requestPaging requestPaging) {
        final PageDto<BlogDto.responsePaging> blogs = blogReadService.paging(requestPaging, SecurityUtil.isAdmin());

        return DataResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(blogs)
                .build();
    }

    @GetMapping("/api/blogs/words")
    public ApiResponse facets(@RequestParam String searchValue) {
        final List<String> facets = blogReadService.getIndexedWordByKeyword(searchValue);

        return CollectionResponse.builder()
                .status(ApiStatus.SUCCESS)
                .result(facets)
                .build();
    }


}
