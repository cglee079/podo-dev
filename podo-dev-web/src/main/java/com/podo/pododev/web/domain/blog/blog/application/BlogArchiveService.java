package com.podo.pododev.web.domain.blog.blog.application;

import com.podo.pododev.web.domain.blog.blog.Blog;
import com.podo.pododev.web.domain.blog.blog.BlogDto;
import com.podo.pododev.web.domain.blog.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BlogArchiveService {

    private final BlogRepository blogRepository;

    @Cacheable(value = "getBlogArchive", key = "#isAdmin")
    public Map<Integer, List<BlogDto.archive>> getArchive(Boolean isAdmin) {
        final Boolean enabled = isAdmin ? null : true;
        final List<Blog> blogs = blogRepository.findAllByEnabledAndOrderByPublishAtDesc(enabled);
        return createYearToBlogs(blogs);
    }

    private Map<Integer, List<BlogDto.archive>> createYearToBlogs(List<Blog> blogs) {
        final Map<Integer, List<BlogDto.archive>> yearToBlogs = new TreeMap<>();

        for (Blog blog : blogs) {
            int year = blog.getPublishAt().getYear();

            List<BlogDto.archive> blogArchives = yearToBlogs.getOrDefault(year, new ArrayList<>());
            blogArchives.add(new BlogDto.archive(blog));
            yearToBlogs.put(year, blogArchives);
        }

        return yearToBlogs;
    }

}
