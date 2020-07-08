package com.podo.pododev.web.domain.blog.blog.dto;

import com.podo.pododev.core.util.DateTimeFormatUtil;
import com.podo.pododev.web.domain.blog.blog.model.Blog;
import lombok.Getter;

@Getter
public class BlogRelates {
    private Long id;
    private String title;
    private Integer commentCount;
    private String createAt;
    private String updateAt;

    public BlogRelates(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.commentCount = blog.getComments().size();
        this.createAt = DateTimeFormatUtil.dateTimeToBeautifulDate(blog.getCreateAt());
        this.updateAt = DateTimeFormatUtil.dateTimeToBeautifulDate(blog.getUpdateAt());
    }
}
