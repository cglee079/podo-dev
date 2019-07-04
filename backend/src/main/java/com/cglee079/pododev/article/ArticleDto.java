package com.cglee079.pododev.article;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

public class ArticleDto {

    public static class insert {
        private String title;
        private String contents;

        public Article toArticle() {
            return Article.builder()
                    .title(this.title)
                    .contents(this.contents)
                    .build();
        }
    }

    @Getter
    public static class update {
        private String title;
        private String contents;
    }


    @Getter
    @Builder
    public static class response {
        private long seq;
        private String title;
        private String contents;
        private Date createAt;
        private long hitCnt;
        private boolean enabled;
    }


}
