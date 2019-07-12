package com.cglee079.pododev.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

public class BlogDto {

    @Getter
    public static class insert {
        private String title;
        private String contents;
    }

    @Getter
    public static class update {
        private String title;
        private String contents;
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class response {
        private long seq;
        private String desc;
        private String title;
        private String contents;
        private long hitCnt;
        private Date createAt;
        private Date updateAt;
        private boolean enabled;

    }


}
