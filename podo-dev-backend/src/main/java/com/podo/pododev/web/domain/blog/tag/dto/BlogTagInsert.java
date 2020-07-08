package com.podo.pododev.web.domain.blog.tag.dto;

import com.podo.pododev.web.domain.blog.tag.model.BlogTag;
import lombok.Getter;

@Getter
public class BlogTagInsert {
    private String tagValue;

    public BlogTag toEntity() {
        return new BlogTag(tagValue);
    }

}
