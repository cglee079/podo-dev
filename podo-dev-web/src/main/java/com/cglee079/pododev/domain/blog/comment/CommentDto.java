package com.cglee079.pododev.domain.blog.comment;

import com.cglee079.pododev.global.util.Formatter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

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

        public response(Comment comment) {
            this.seq = comment.getSeq();
            this.username = comment.getUsername();
            this.contents = comment.getContents();
            this.createAt = Formatter.dateTimeToStr(comment.getCreateAt());
        }
    }
}
