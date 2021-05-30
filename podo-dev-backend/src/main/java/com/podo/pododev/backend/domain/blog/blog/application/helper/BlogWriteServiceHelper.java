package com.podo.pododev.backend.domain.blog.blog.application.helper;

import com.podo.pododev.backend.domain.blog.blog.model.Blog;
import com.podo.pododev.backend.domain.blog.tag.dto.BlogTagInsert;
import com.podo.pododev.backend.domain.blog.tag.repository.BlogTagRepository;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class BlogWriteServiceHelper {

    public static void saveBlogTags(Blog blog, List<BlogTagInsert> tags, BlogTagRepository blogTagRepository) {
        blogTagRepository.deleteAll(blog.getTags());
        blog.clearTags();


        for (BlogTagInsert tagInsert : tags) {
            blog.addTag(blogTagRepository.save(tagInsert.toEntity()));
        }
    }
}
