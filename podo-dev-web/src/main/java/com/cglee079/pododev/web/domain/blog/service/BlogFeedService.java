package com.cglee079.pododev.web.domain.blog.service;

import com.cglee079.pododev.web.domain.blog.Blog;
import com.cglee079.pododev.web.domain.blog.BlogDto;
import com.cglee079.pododev.web.domain.blog.repository.BlogRepository;
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

    /**
     * 노출 게시글 조회
     */
    public List<BlogDto.feed> findByEnabled() {
        List<Blog> blogs = blogRepository.findByEnabled(true);
        return blogs.stream()
                .map(BlogDto.feed::new)
                .collect(Collectors.toList());
    }


    /**
     * 웹피드 되어야할 게시글이 있는가?
     */
    public Boolean hasYetNotFeed(Boolean feeded) {
        return !blogRepository.findByFeeded(feeded).isEmpty();
    }

    public void completeFeed() {
        blogRepository.findByFeeded(false).forEach(Blog::doFeeded);
    }


}
