package com.podo.pododev.web.global.infra.solr;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@EqualsAndHashCode
public class BlogSearchResultVo {
    private Long blogId;
    private String title;
    private String contents;

    public BlogSearchResultVo(String blogId, String title, String contents) {
        this.blogId = Long.valueOf(blogId);
        this.title = title;
        this.contents = contents;
    }
}
