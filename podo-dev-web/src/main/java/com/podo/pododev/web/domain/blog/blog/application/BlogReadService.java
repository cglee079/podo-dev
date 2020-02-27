package com.podo.pododev.web.domain.blog.blog.application;

import com.podo.pododev.core.rest.response.PageDto;
import com.podo.pododev.web.domain.blog.AttachStatus;
import com.podo.pododev.web.domain.blog.blog.Blog;
import com.podo.pododev.web.domain.blog.blog.BlogDto;
import com.podo.pododev.web.domain.blog.blog.exception.InvalidBlogIdException;
import com.podo.pododev.web.domain.blog.blog.repository.BlogRepository;
import com.podo.pododev.web.domain.blog.tag.BlogTag;
import com.podo.pododev.web.global.infra.solr.BlogSearchResultVo;
import com.podo.pododev.web.global.infra.solr.MySolrClient;
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

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BlogReadService {

    @Value("${blog.page.size}")
    private Integer pageSize;

    @Value("${blog.relates.size}")
    private Integer relatesSize;

    private final AttachLinkManager attachLinkManager;
    private final BlogRepository blogRepository;
    private final MySolrClient mySolrClient;


    public List<String> getIndexedWordByKeyword(String keyword) {
        return mySolrClient.getIndexedWordsByKeyword(keyword);
    }

    @Cacheable(value = "getBlogArchive", key = "#isAdmin")
    public Map<Integer, List<BlogDto.archive>> getArchiveMapByYearOfPublishAt(Boolean isAdmin) {
        final Boolean enabled = isAdmin ? null : true;
        final List<Blog> blogs = blogRepository.findAllByEnabledAndOrderByPublishAtDesc(enabled);
        return toMapByYearOfPublishAt(blogs);
    }

    private Map<Integer, List<BlogDto.archive>> toMapByYearOfPublishAt(List<Blog> blogs) {
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


    @Cacheable(value = "getBlog", key = "#blogId")
    public BlogDto.response getExistedBlogByBlogId(Long blogId) {
        final Optional<Blog> blogOptional = blogRepository.findById(blogId);

        if (!blogOptional.isPresent()) {
            throw new InvalidBlogIdException(blogId);
        }

        final Blog blog = blogOptional.get();

        final LocalDateTime publishAt = blog.getPublishAt();
        final Blog beforeBlog = blogRepository.findOneBeforePublishAt(publishAt);
        final Blog nextBlog = blogRepository.findOneAfterPublishAt(publishAt);

        final List<String> tagValues = blog.getTags().stream()
                .map(BlogTag::getTagValue)
                .collect(toList());

        final List<Blog> relateBlogs = getRelatesByTagValues(tagValues);

        return new BlogDto.response(blog, beforeBlog, nextBlog, relateBlogs, attachLinkManager.getStorageStaticUrl(), AttachStatus.BE);
    }

    private List<Blog> getRelatesByTagValues(List<String> tagValues) {
        if (tagValues.isEmpty()) {
            return Collections.emptyList();
        }

        final List<Blog> relates = blogRepository.findByTagValues(tagValues.get(0), tagValues.subList(1, tagValues.size()));
        final Map<Blog, Integer> scores = getRelateScore(tagValues, relates);

        return scores.entrySet().stream()
                .sorted(getRelateSortComparator())
                .map(Map.Entry::getKey)
                .limit(relatesSize)
                .collect(toList());
    }


    private Map<Blog, Integer> getRelateScore(List<String> tagValues, List<Blog> relates) {

        final Map<Blog, Integer> scores = new HashMap<>();

        //중복되는 태그가 많을수록 상위 순위
        for (String tag : tagValues) {
            for (Blog relate : relates) {

                List<String> tags = relate.getTags().stream()
                        .map(BlogTag::getTagValue)
                        .collect(toList());

                if (tags.contains(tag)) {
                    scores.merge(relate, 1, Integer::sum);
                }
            }
        }

        return scores;
    }

    private Comparator<Map.Entry<Blog, Integer>> getRelateSortComparator() {
        return (relateOne, relateTwo) -> {
            final Integer relateOneScore = relateOne.getValue();
            final Integer relateTwoScore = relateTwo.getValue();
            int sort = relateTwoScore.compareTo(relateOneScore);

            if (sort != 0) {
                return sort;
            }

            //태그 순위가 같을 경우 발행일순
            final LocalDateTime relateTwoPublishAt = relateTwo.getKey().getPublishAt();
            final LocalDateTime relateOnePublishAt = relateOne.getKey().getPublishAt();
            return relateTwoPublishAt.compareTo(relateOnePublishAt);
        };
    }


    @Cacheable(value = "pagingBlogs", key = "T(String).valueOf(#requestPaging.hashCode())  + #isAdmin.toString()")
    public PageDto<BlogDto.responsePaging> paging(BlogDto.requestPaging requestPaging, Boolean isAdmin) {
        final String searchValue = requestPaging.getSearch();
        final Integer page = requestPaging.getPage();
        final String tagValue = requestPaging.getTag();
        final Pageable pageable = PageRequest.of(page, pageSize);
        final Boolean enabled = isAdmin ? null : true;

        if (!StringUtils.isEmpty(searchValue)) {
            return pagingBySearchValue(searchValue, pageable, enabled);
        }

        if (!StringUtils.isEmpty(tagValue)) {
            return pagingByFilterTag(tagValue, pageable, enabled);
        }

        return pagingDefault(pageable, enabled);
    }

    private PageDto<BlogDto.responsePaging> pagingDefault(Pageable pageable, Boolean enabled) {
        final Page<Blog> blogs = blogRepository.paging(pageable, null, enabled);

        final List<BlogDto.responsePaging> contents = blogs.stream()
                .map(blog -> new BlogDto.responsePaging(blog, attachLinkManager.getStorageStaticUrl()))
                .collect(toList());

        return PageDto.<BlogDto.responsePaging>builder()
                .contents(contents)
                .currentPage(blogs.getPageable().getPageNumber())
                .pageSize(blogs.getPageable().getPageSize())
                .totalElements(blogs.getTotalElements())
                .totalPages(blogs.getTotalPages())
                .build();
    }

    private PageDto<BlogDto.responsePaging> pagingBySearchValue(String searchValue, Pageable pageable, Boolean enabled) {
        final List<BlogSearchResultVo> results = mySolrClient.search(searchValue);
        final List<Long> ids = results.stream()
                .map(BlogSearchResultVo::getBlogId)
                .collect(toList());

        final Map<Long, String> highlightDescription = results.stream().collect(Collectors.toMap(BlogSearchResultVo::getBlogId, BlogSearchResultVo::getContents));

        final Page<Blog> blogs = blogRepository.paging(pageable, ids, enabled);

        final List<BlogDto.responsePaging> contents = blogs.stream()
                .map(blog -> BlogDto.responsePaging.createWithHighlightDescription(blog, highlightDescription.get(blog.getId()), attachLinkManager.getStorageStaticUrl()))
                .collect(toList());

        return PageDto.<BlogDto.responsePaging>builder()
                .contents(contents)
                .currentPage(blogs.getPageable().getPageNumber())
                .pageSize(blogs.getPageable().getPageSize())
                .totalElements(blogs.getTotalElements())
                .totalPages(blogs.getTotalPages())
                .build();

    }

    private PageDto<BlogDto.responsePaging> pagingByFilterTag(String tagValue, Pageable pageable, Boolean enabled) {
        final List<Long> blogIds = blogRepository.findByTagValues(tagValue, null).stream()
                .map(Blog::getId)
                .collect(toList());

        final Page<Blog> blogs = blogRepository.paging(pageable, blogIds, enabled);

        final List<BlogDto.responsePaging> contents = blogs.stream()
                .map(blog -> new BlogDto.responsePaging(blog, attachLinkManager.getStorageStaticUrl()))
                .collect(toList());

        return PageDto.<BlogDto.responsePaging>builder()
                .contents(contents)
                .currentPage(blogs.getPageable().getPageNumber())
                .pageSize(blogs.getPageable().getPageSize())
                .totalElements(blogs.getTotalElements())
                .totalPages(blogs.getTotalPages())
                .build();
    }

}
