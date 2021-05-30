package com.podo.pododev.backend.domain.blog.tag.dto;

import com.podo.pododev.backend.domain.blog.tag.model.BlogTag;
import lombok.Getter;

@Getter
public class BlogTagResponse {
    private Long id;
    private String tagValue;

    public BlogTagResponse(BlogTag blogTag) {
        this.id = blogTag.getId();
        this.tagValue = blogTag.getTagValue();
    }
}
