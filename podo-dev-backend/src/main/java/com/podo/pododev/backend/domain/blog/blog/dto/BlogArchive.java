package com.podo.pododev.backend.domain.blog.blog.dto;

import com.podo.pododev.core.util.DateTimeFormatUtil;
import com.podo.pododev.backend.domain.blog.blog.model.Blog;
import lombok.Getter;

@Getter
public class BlogArchive {
    private Long id;
    private String title;
    private String publishAt;
    private Boolean enabled;

    public BlogArchive(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.publishAt = DateTimeFormatUtil.dateTimeToBeautifulDate(blog.getPublishAt());
        this.enabled = blog.getEnabled();
    }
}
