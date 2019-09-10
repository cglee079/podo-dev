package com.cglee079.pododev.web.domain.blog.comment;

import com.cglee079.pododev.web.global.util.Formatter;
import com.cglee079.pododev.web.global.util.MarkdownUtil;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

public class CommentDto {

    @Getter
    public static class insert {
        @NotEmpty(message = "내용을 입력해주세요")
        private String contents;

        private Long parentSeq;
    }


    @Getter
    public static class response {
        private Long seq;
        private String username;
        private String contents;
        private String createAt;
        private Integer depth;
        private Boolean enabled;
        private Boolean isMine;

        public response(Comment comment, String currentUserId) {
            this.seq = comment.getSeq();
            this.username = comment.getUsername();
            this.contents = MarkdownUtil.line2br(MarkdownUtil.escape(comment.getContents()));
            this.depth = comment.getDepth();
            this.createAt = Formatter.dateTimeToBeautifulDate(comment.getCreateAt());
            this.enabled = comment.getEnabled();
            this.isMine = comment.getUserId().equalsIgnoreCase(currentUserId);
        }

    }
}
