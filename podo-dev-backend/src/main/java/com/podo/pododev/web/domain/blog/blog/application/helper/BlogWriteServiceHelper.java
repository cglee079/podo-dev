package com.podo.pododev.web.domain.blog.blog.application.helper;

import com.podo.pododev.web.domain.blog.blog.Blog;
import com.podo.pododev.web.domain.blog.tag.BlogTag;
import com.podo.pododev.web.domain.blog.tag.BlogTagDto;
import com.podo.pododev.web.domain.blog.tag.repository.BlogTagRepository;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class BlogWriteServiceHelper {

    public static void saveBlogTags(Blog blog, List<BlogTagDto.insert> tags, BlogTagRepository blogTagRepository) {
        blogTagRepository.deleteAll(blog.getTags());
        blog.clearTags();


        for (BlogTagDto.insert tagInsert : tags) {
            blog.addTag(blogTagRepository.save(tagInsert.toEntity()));
        }
    }
}
