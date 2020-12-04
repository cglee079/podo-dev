package com.podo.pododev.web.domain.blog.blog.application;

import com.podo.pododev.web.domain.blog.attach.AttachStatus;
import com.podo.pododev.web.domain.blog.blog.application.helper.BlogServiceHelper;
import com.podo.pododev.web.domain.blog.blog.dto.BlogResponse;
import com.podo.pododev.web.domain.blog.blog.model.Blog;
import com.podo.pododev.web.domain.blog.blog.repository.BlogRepository;
import com.podo.pododev.web.domain.blog.tag.model.BlogTag;
import com.podo.pododev.web.global.config.aop.argschecker.AllArgsNotNull;
import com.podo.pododev.web.global.util.AttachLinkManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BlogReadService {

    @Value("${blog.relates.size}")
    private Integer relatesSize;

    private final BlogRepository blogRepository;
    private final AttachLinkManager attachLinkManager;

    public String getBlogTitleById(Long blogId) {
        return BlogServiceHelper.findByBlogId(blogId, blogRepository).getTitle();
    }

    @Cacheable(value = "getBlog", key = "T(String).valueOf(#blogId) + #isAdmin.toString()")
    public BlogResponse getBlogById(Long blogId, Boolean isAdmin) {
        final Blog blog = BlogServiceHelper.findByBlogId(blogId, blogRepository);
        final Boolean enabled = isAdmin ? null : true;

        final LocalDateTime publishAt = blog.getPublishAt();
        final Blog beforeBlog = blogRepository.findOneBeforePublishAt(publishAt, enabled);
        final Blog nextBlog = blogRepository.findOneAfterPublishAt(publishAt, enabled);

        final List<String> tagValues = blog.getTags().stream()
                .map(BlogTag::getTagValue)
                .collect(toList());

        final List<Blog> relateBlogs = getRelatesByTagValues(tagValues, enabled);

        return new BlogResponse(blog, beforeBlog, nextBlog, relateBlogs, attachLinkManager.getStorageStaticUrl(), AttachStatus.BE);
    }


    @AllArgsNotNull
    private List<Blog> getRelatesByTagValues(List<String> tagValues, Boolean enabled) {
        if (tagValues.isEmpty()) {
            return Collections.emptyList();
        }

        final List<Blog> relates = blogRepository.findByTagValues(tagValues, enabled);
        final Map<Blog, Integer> scores = scoreRelate(tagValues, relates);

        return scores.entrySet().stream()
                .sorted(getComparatorOfBlogRelate())
                .map(Map.Entry::getKey)
                .limit(relatesSize)
                .collect(toList());
    }


    private Map<Blog, Integer> scoreRelate(List<String> tagValues, List<Blog> relates) {

        final Map<Blog, Integer> scores = new HashMap<>();

        //중복되는 태그가 많을수록 상위 순위
        for (String tag : tagValues) {
            for (Blog relate : relates) {

                List<String> tags = relate.getTags().stream()
                        .map(BlogTag::getTagValue)
                        .map(String::toLowerCase)
                        .collect(toList());

                if (tags.contains(tag.toLowerCase())) {
                    scores.merge(relate, 1, Integer::sum);
                }
            }
        }

        return scores;
    }

    private Comparator<Map.Entry<Blog, Integer>> getComparatorOfBlogRelate() {
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


}
