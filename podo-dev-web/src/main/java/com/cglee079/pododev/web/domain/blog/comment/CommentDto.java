package com.cglee079.pododev.web.domain.blog.comment;

import com.cglee079.pododev.web.global.util.Formatter;
import lombok.Getter;

public class CommentDto {

    @Getter
    public static class insert {
        private String username;
        private String password;
        private String contents;
        private Long parentSeq;
    }


    @Getter
    public static class response {
        private Long seq;
        private String username;
        private String contents;
        private String createAt;
        private Boolean enabled;

        public response(Comment comment) {
            this.seq = comment.getSeq();
            this.username = comment.getUsername();
            this.contents = comment.getContents();
            this.createAt = Formatter.dateTimeToStr(comment.getCreateAt());
            this.enabled = comment.getEnabled();
        }
    }
}
