package com.podo.pododev.web.domain.blog.blog.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class BlogRequestPaging {
    private Integer page;
    private String tag;
    private String search;
}
