package com.podo.pododev.web.domain.blog.blog.application;

import com.podo.pododev.core.rest.response.dto.PageDto;
import com.podo.pododev.web.domain.blog.blog.model.Blog;
import com.podo.pododev.web.domain.blog.blog.dto.BlogRequestPaging;
import com.podo.pododev.web.domain.blog.blog.dto.BlogResponsePaging;
import com.podo.pododev.web.domain.blog.blog.repository.BlogRepository;
import com.podo.pododev.web.global.infra.solr.dto.BlogSearchResult;
import com.podo.pododev.web.global.infra.solr.SolrSearchService;
import com.podo.pododev.web.global.util.AttachLinkManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BlogPagingService {

    @Value("${blog.page.size}")
    private Integer pageSize;

    private final AttachLinkManager attachLinkManager;
    private final BlogRepository blogRepository;
    private final SolrSearchService solrSearchService;

    @Cacheable(value = "pagingBlogs", key = "T(String).valueOf(#requestPaging.hashCode()) + #isAdmin.toString()")
    public PageDto<BlogResponsePaging> paging(BlogRequestPaging requestPaging, Boolean isAdmin) {
        final String searchValue = requestPaging.getSearch();
        final Integer page = requestPaging.getPage();
        final String tagValue = requestPaging.getTag();
        final Pageable pageable = PageRequest.of(page, pageSize);
        final Boolean enabled = isAdmin ? null : true;

        if (StringUtils.hasText(searchValue)) {
            return pagingBySearchValue(searchValue, pageable, enabled);
        }

        if (StringUtils.hasText(tagValue)) {
            return pagingByFilterTag(tagValue, pageable, enabled);
        }

        return pagingDefault(pageable, enabled);
    }

    private PageDto<BlogResponsePaging> pagingDefault(Pageable pageable, Boolean enabled) {
        final Page<Blog> blogs = blogRepository.paging(pageable, enabled);

        final List<BlogResponsePaging> contents = blogs.stream()
                .map(blog -> new BlogResponsePaging(blog, attachLinkManager.getStorageStaticUrl()))
                .collect(toList());

        return createPageDto(blogs, contents);
    }

    private PageDto<BlogResponsePaging> pagingBySearchValue(String searchValue, Pageable pageable, Boolean enabled) {
        final List<BlogSearchResult> results = solrSearchService.search(searchValue);
        final List<Long> ids = results.stream()
                .map(BlogSearchResult::getBlogId)
                .collect(toList());

        final Map<Long, String> highlightDescription = results.stream().collect(Collectors.toMap(BlogSearchResult::getBlogId, BlogSearchResult::getContents));

        final Page<Blog> blogs = blogRepository.paging(pageable, ids, enabled);

        final List<BlogResponsePaging> contents = blogs.stream()
                .map(blog -> BlogResponsePaging.createWithHighlightDescription(blog, highlightDescription.get(blog.getId()), attachLinkManager.getStorageStaticUrl()))
                .collect(toList());

        return createPageDto(blogs, contents);
    }

    private PageDto<BlogResponsePaging> pagingByFilterTag(String tagValue, Pageable pageable, Boolean enabled) {
        final List<Long> blogIds = blogRepository.findByTagValues(Collections.singletonList(tagValue), enabled).stream()
                .map(Blog::getId)
                .collect(toList());

        final Page<Blog> blogs = blogRepository.paging(pageable, blogIds, enabled);

        final List<BlogResponsePaging> contents = blogs.stream()
                .map(blog -> new BlogResponsePaging(blog, attachLinkManager.getStorageStaticUrl()))
                .collect(toList());

        return createPageDto(blogs, contents);
    }

    private PageDto<BlogResponsePaging> createPageDto(Page<Blog> blogs, List<BlogResponsePaging> contents) {
        return PageDto.<BlogResponsePaging>builder()
                .contents(contents)
                .currentPage(blogs.getPageable().getPageNumber())
                .pageSize(blogs.getPageable().getPageSize())
                .totalElements(blogs.getTotalElements())
                .totalPages(blogs.getTotalPages())
                .build();
    }

}
