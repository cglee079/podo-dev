package com.podo.pododev.web.domain.blog.blog.application.helper;

import com.podo.pododev.web.domain.blog.blog.model.Blog;
import com.podo.pododev.web.domain.blog.blog.exception.InvalidBlogIdApiException;
import com.podo.pododev.web.domain.blog.blog.repository.BlogRepository;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class BlogServiceHelper {

    public static Blog findByBlogId(Long blogId, BlogRepository blogRepository) {
        final Optional<Blog> blogOptional = blogRepository.findById(blogId);

        return blogOptional.orElseThrow(() -> new InvalidBlogIdApiException(blogId));
    }
}
