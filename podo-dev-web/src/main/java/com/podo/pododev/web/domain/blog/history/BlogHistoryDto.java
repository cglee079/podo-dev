package com.podo.pododev.web.domain.blog.history;

import com.podo.pododev.core.util.DateTimeFormatUtil;
import lombok.Getter;

public class BlogHistoryDto {

    @Getter
    public static class response {

        private long id;
        private String title;
        private String contents;

        public response(BlogHistory blogHistory) {

            this.id = blogHistory.getId();
            this.title = blogHistory.getTitle();
            this.contents = blogHistory.getContents();
        }
    }

    @Getter
    public static class responses{

        private long id;
        private String title;
        private String createAt;

        public responses(BlogHistory blogHistory) {

            this.id = blogHistory.getId();
            this.title = blogHistory.getTitle();
            this.createAt = DateTimeFormatUtil.dateTimeToDateTimeStr(blogHistory.getCreateAt());
        }
    }
}
