package com.podo.pododev.web.domain.blog.history.dto;

import com.podo.pododev.core.util.DateTimeFormatUtil;
import com.podo.pododev.web.domain.blog.history.model.BlogHistory;
import lombok.Getter;

@Getter
public class BlogHistoryResponses {

    private Long id;
    private String title;
    private String createAt;

    public BlogHistoryResponses(BlogHistory blogHistory) {

        this.id = blogHistory.getId();
        this.title = blogHistory.getTitle();
        this.createAt = DateTimeFormatUtil.dateTimeToDateTimeStr(blogHistory.getCreateAt());
    }
}
