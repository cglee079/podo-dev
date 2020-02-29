package com.podo.pododev.web.domain.blog.blog.api;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.response.*;
import com.podo.pododev.core.rest.response.dto.PageDto;
import com.podo.pododev.web.domain.blog.blog.BlogDto;
import com.podo.pododev.web.domain.blog.blog.application.BlogArchiveService;
import com.podo.pododev.web.domain.blog.blog.application.BlogPagingService;
import com.podo.pododev.web.domain.blog.blog.application.BlogReadService;
import com.podo.pododev.web.global.infra.solr.MySolrClient;
import com.podo.pododev.web.global.util.SecurityUtil;
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
    private final BlogPagingService blogPagingService;
    private final BlogArchiveService blogArchiveService;
    private final MySolrClient mySolrClient;

    @GetMapping("/api/blogs/archive")
    public ApiResponse getArchive() {

        final Map<Integer, List<BlogDto.archive>> archive = blogArchiveService.getArchive(SecurityUtil.isAdmin());

        return DataResponse.success()
                .result(archive)
                .build();
    }

    @GetMapping("/api/blogs/{blogId}")
    public ApiResponse findByBlogId(@PathVariable Long blogId) {
        final BlogDto.response blog = blogReadService.getBlogById(blogId, SecurityUtil.isAdmin());

        return DataResponse.success()
                .result(blog)
                .build();
    }

    @GetMapping("/api/blogs")
    public ApiResponse paging(BlogDto.requestPaging requestPaging) {
        final PageDto<BlogDto.responsePaging> blogs = blogPagingService.paging(requestPaging, SecurityUtil.isAdmin());

        return DataResponse.success()
                .result(blogs)
                .build();
    }

    @GetMapping("/api/blogs/words")
    public ApiResponse facets(@RequestParam String searchValue) {
        final List<String> facets = mySolrClient.getIndexedWordsByKeyword(searchValue);

        return CollectionResponse.success()
                .result(facets)
                .build();
    }


}
