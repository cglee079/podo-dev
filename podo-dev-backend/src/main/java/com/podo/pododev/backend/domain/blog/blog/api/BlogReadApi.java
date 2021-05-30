package com.podo.pododev.backend.domain.blog.blog.api;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.response.CollectionResponse;
import com.podo.pododev.core.rest.response.dto.PageDto;
import com.podo.pododev.backend.domain.blog.blog.application.BlogArchiveService;
import com.podo.pododev.backend.domain.blog.blog.application.BlogPagingService;
import com.podo.pododev.backend.domain.blog.blog.application.BlogReadService;
import com.podo.pododev.backend.domain.blog.blog.dto.BlogArchive;
import com.podo.pododev.backend.domain.blog.blog.dto.BlogRequestPaging;
import com.podo.pododev.backend.domain.blog.blog.dto.BlogResponse;
import com.podo.pododev.backend.domain.blog.blog.dto.BlogResponsePaging;
import com.podo.pododev.backend.global.external.solr.SolrSearchService;
import com.podo.pododev.backend.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private final SolrSearchService solrSearchService;

    @GetMapping("/api/blogs/archive")
    public Map<Integer, List<BlogArchive>> getArchive() {
        return blogArchiveService.getArchive(SecurityUtil.isAdmin());
    }

    @GetMapping("/api/blogs/{blogId}")
    public BlogResponse findByBlogId(@PathVariable Long blogId) {
        return blogReadService.getBlogById(blogId, SecurityUtil.isAdmin());
    }

    @GetMapping("/api/blogs")
    public PageDto<BlogResponsePaging> paging(BlogRequestPaging requestPaging) {
        return blogPagingService.paging(requestPaging, SecurityUtil.isAdmin());
    }

    @GetMapping("/api/blogs/words")
    public ApiResponse facets(@RequestParam String searchValue) {
        final List<String> facets = solrSearchService.getIndexedWordsByKeyword(searchValue);
        return CollectionResponse.ok(facets);
    }

}
