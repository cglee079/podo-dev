package com.podo.pododev.web.domain.blog.blog;

import com.podo.pododev.web.domain.blog.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class BlogSetup {

    private final BlogRepository blogRepository;

    public Blog saveOne(){
        Blog blog = Blog.builder()
                .title("blog")
                .contents("contents")
                .enabled(true)
                .hitCount(0)
                .webFeeded(false)
                .attachFiles(Collections.emptyList())
                .attachImages(Collections.emptyList())
                .build();

        return blogRepository.save(blog);
    }
}
