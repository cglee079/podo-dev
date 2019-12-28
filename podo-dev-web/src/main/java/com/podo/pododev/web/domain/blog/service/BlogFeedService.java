package com.podo.pododev.web.domain.blog.service;

import com.podo.pododev.web.domain.blog.Blog;
import com.podo.pododev.web.domain.blog.BlogDto;
import com.podo.pododev.web.domain.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BlogFeedService {

    private final BlogRepository blogRepository;

    public List<BlogDto.feed> findByEnabledOrderByPublishDesc() {
        List<Blog> exitedBlogs = blogRepository.findByEnabledOrderByPublishDesc(true);
        return exitedBlogs.stream()
                .map(BlogDto.feed::new)
                .collect(Collectors.toList());
    }

    public Boolean existByFeeded(boolean feeded) {
        final List<Blog> blogs = blogRepository.findByWebFeeded(feeded);
        return !blogs.isEmpty();
    }

    public void completeFeed() {
        final List<Blog> noWebFeededBlogs = blogRepository.findByWebFeeded(false);

        for (Blog noWebFeededBlog : noWebFeededBlogs) {
            noWebFeededBlog.doWebFeeded();
        }
    }


}
