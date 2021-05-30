package com.podo.pododev.backend.global.external.solr.dto;

import lombok.Getter;

@Getter
public class BlogSearchResult {
    private Long blogId;
    private String title;
    private String contents;

    public BlogSearchResult(String blogId, String title, String contents) {
        this.blogId = Long.valueOf(blogId);
        this.title = title;
        this.contents = contents;
    }
}
