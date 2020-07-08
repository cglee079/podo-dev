package com.podo.pododev.web.domain.blog.history.dto;

import com.podo.pododev.web.domain.blog.history.model.BlogHistory;
import lombok.Getter;

@Getter
public class BlogHistoryResponse {

    private Long id;
    private String title;
    private String contents;

    public BlogHistoryResponse(BlogHistory blogHistory) {

        this.id = blogHistory.getId();
        this.title = blogHistory.getTitle();
        this.contents = blogHistory.getContents();
    }
}
