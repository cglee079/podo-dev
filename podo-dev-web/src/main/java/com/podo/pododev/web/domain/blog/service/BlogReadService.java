package com.podo.pododev.web.domain.blog.service;

import com.podo.pododev.core.rest.response.PageDto;
import com.podo.pododev.web.domain.blog.Blog;
import com.podo.pododev.web.domain.blog.BlogDto;
import com.podo.pododev.web.domain.blog.repository.BlogRepository;
import com.podo.pododev.web.domain.blog.FileStatus;
import com.podo.pododev.web.domain.blog.exception.InvalidBlogIdException;
import com.podo.pododev.web.domain.blog.tag.BlogTag;
import com.podo.pododev.web.global.config.security.SecurityUtil;
import com.podo.pododev.web.global.infra.solr.BlogSearchVo;
import com.podo.pododev.web.global.infra.solr.MySolrClient;
import com.podo.pododev.web.global.util.AttachLinkManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BlogReadService {

    @Value("${blog.per.page.size}")
    private Integer pageSize;

    @Value("${blog.relates.size}")
    private Integer relatesSize;

    private final AttachLinkManager attachLinkManager;
    private final BlogRepository blogRepository;
    private final MySolrClient mySolrClient;


    public List<String> facets(String value) {
        return mySolrClient.getFacets(value);
    }

    public Map<Integer, List<BlogDto.archive>> getArchive() {
        final List<Blog> blogs = blogRepository.findAllByEnabledAndOrderByPublishAtDesc(isShowAllBlogs());
        return toMapByPublishYear(blogs);
    }

    private Map<Integer, List<BlogDto.archive>> toMapByPublishYear(List<Blog> blogs) {
        final Map<Integer, List<BlogDto.archive>> mapByYear = new TreeMap<>();

        for (Blog blog : blogs) {
            int year = blog.getPublishAt().getYear();

            List<BlogDto.archive> blogArchives = mapByYear.get(year);

            if (Objects.isNull(blogArchives)) {
                blogArchives = new ArrayList<>();
                mapByYear.put(year, blogArchives);
            }

            blogArchives.add(new BlogDto.archive(blog));
        }

        return mapByYear;
    }


    public BlogDto.response get(Long blogId) {
        final Optional<Blog> blogOptional = blogRepository.findById(blogId);

        if (!blogOptional.isPresent()) {
            throw new InvalidBlogIdException();
        }

        final Blog blog = blogOptional.get();

        final Blog next = blogRepository.findNext(blogId);
        final Blog before = blogRepository.findBefore(blogId);

        final List<String> tagValues = blog.getTags().stream()
                .map(BlogTag::getVal)
                .collect(Collectors.toList());

        final List<Blog> relates = getRelates(tagValues);

        return new BlogDto.response(blog, before, next, relates, attachLinkManager.getStorageStaticLink(), FileStatus.BE);
    }

    private List<Blog> getRelates(List<String> tagValues) {

        if (tagValues.isEmpty()) {
            return Collections.emptyList();
        }

        final List<Blog> relates = blogRepository.findByTagValues(tagValues.get(0), tagValues.subList(1, tagValues.size()));
        final Map<Blog, Integer> scores = getRelateScore(tagValues, relates);

        return scores.entrySet().stream()
                .sorted((e1, e2) -> {
                    int sort = e2.getValue().compareTo(e1.getValue()); //역순

                    if (sort != 0) {
                        return sort;
                    }

                    //태그 순위가 같을 경우 발행일순
                    return e2.getKey().getPublishAt().compareTo(e1.getKey().getPublishAt());
                })
                .map(Map.Entry::getKey)
                .limit(relatesSize)
                .collect(Collectors.toList());

    }

    private Map<Blog, Integer> getRelateScore(List<String> tagValues, List<Blog> relates) {

        final Map<Blog, Integer> scores = new HashMap<>();

        //중복되는 태그가 많을수록 상위 순위
        for (String tag : tagValues) {
            for (Blog relate : relates) {

                List<String> tags = relate.getTags().stream()
                        .map(BlogTag::getVal)
                        .collect(Collectors.toList());

                if (tags.contains(tag)) {
                    scores.merge(relate, 1, Integer::sum);
                }

            }

        }

        return scores;
    }


    public PageDto paging(BlogDto.request request) {
        final String search = request.getSearch();
        final Integer page = request.getPage();
        final String tagValue = request.getTag();
        final Pageable pageable = PageRequest.of(page, pageSize);
        final Boolean enabled = isShowAllBlogs();

        //Filter By Search(검색)
        if (!StringUtils.isEmpty(search)) {
            return pagingBySearch(search, pageable, enabled);
        }

        //Filter By Tag
        else if (!StringUtils.isEmpty(tagValue)) {
            return pagingByFilterTag(tagValue, pageable, enabled);
        }

        //모든 게시글
        else {
            return pagingDefault(pageable, enabled);
        }


    }

    private PageDto pagingDefault(Pageable pageable, Boolean enabled) {
        final Page<Blog> blogs = blogRepository.paging(pageable, null, enabled);

        final List<BlogDto.responseList> contents = blogs.stream()
                .map(blog -> new BlogDto.responseList(blog, attachLinkManager.getStorageStaticLink()))
                .collect(Collectors.toList());

        return PageDto.<BlogDto.responseList>builder()
                .contents(contents)
                .currentPage(blogs.getPageable().getPageNumber())
                .pageSize(blogs.getPageable().getPageSize())
                .totalElements(blogs.getTotalElements())
                .totalPages(blogs.getTotalPages())
                .build();
    }

    private PageDto<BlogDto.responseList> pagingBySearch(String search, Pageable pageable, Boolean enabled) {
        final List<BlogSearchVo> results = mySolrClient.search(search);
        final List<Long> ids = results.stream()
                .map(BlogSearchVo::getBlogId)
                .collect(Collectors.toList());

        final Map<Long, String> desc = results.stream().collect(Collectors.toMap(BlogSearchVo::getBlogId, BlogSearchVo::getContents));

        final Page<Blog> blogs = blogRepository.paging(pageable, ids, enabled);

        final List<BlogDto.responseList> contents = blogs.stream()
                .map(blog -> new BlogDto.responseList(blog, desc.get(blog.getId()), attachLinkManager.getStorageStaticLink()))
                .collect(Collectors.toList());

        return PageDto.<BlogDto.responseList>builder()
                .contents(contents)
                .currentPage(blogs.getPageable().getPageNumber())
                .pageSize(blogs.getPageable().getPageSize())
                .totalElements(blogs.getTotalElements())
                .totalPages(blogs.getTotalPages())
                .build();

    }

    private PageDto pagingByFilterTag(String tagValue, Pageable pageable, Boolean enabled) {
        final List<Long> ids = blogRepository.findByTagValues(tagValue, null).stream()
                .map(Blog::getId)
                .collect(Collectors.toList());

        final Page<Blog> blogs = blogRepository.paging(pageable, ids, enabled);
        final List<BlogDto.responseList> contents = blogs.stream()
                .map(blog -> new BlogDto.responseList(blog, attachLinkManager.getStorageStaticLink()))
                .collect(Collectors.toList());

        return PageDto.<BlogDto.responseList>builder()
                .contents(contents)
                .currentPage(blogs.getPageable().getPageNumber())
                .pageSize(blogs.getPageable().getPageSize())
                .totalElements(blogs.getTotalElements())
                .totalPages(blogs.getTotalPages())
                .build();
    }

    private Boolean isShowAllBlogs() {
        Boolean enabled = true;

        // If Admin, Show All Blogs (Include Disabled)
        if (SecurityUtil.isAdmin()) {
            enabled = null;
        }

        return enabled;
    }

}
