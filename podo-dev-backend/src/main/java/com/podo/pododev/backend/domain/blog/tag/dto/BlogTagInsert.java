package com.podo.pododev.backend.domain.blog.tag.dto;

import com.podo.pododev.backend.domain.blog.tag.model.BlogTag;
import lombok.Getter;

@Getter
public class BlogTagInsert {
    private String tagValue;

    public BlogTag toEntity() {
        return new BlogTag(tagValue);
    }

}
