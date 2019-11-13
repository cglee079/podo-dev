package com.cglee079.pododev.web.domain.blog.comment;

import com.cglee079.pododev.web.domain.blog.Blog;
import com.cglee079.pododev.core.global.util.FormatUtil;
import com.cglee079.pododev.core.global.util.MarkdownUtil;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

public class CommentDto {


    @Setter
    @Getter
    public static class request {
        private Integer page;
    }

    @Getter
    public static class insert {
        @NotEmpty(message = "내용을 입력해주세요")
        private String contents;

        private Long parentId;
    }


    @Getter
    public static class summary {
        private Long id;
        private String username;
        private Long blogId;
        private String blogTitle;
        private String contents;
        private String createAt;

        public summary(Comment comment) {
            final Blog blog = comment.getBlog();

            this.id = comment.getId();
            this.blogId = blog.getId();
            this.blogTitle = blog.getTitle();
            this.username = comment.getUser().getUsername();
            this.contents = comment.getContents().replace("\n", " ");
            this.createAt = FormatUtil.dateTimeToBeautifulDate(comment.getCreateAt());
        }
    }

    @Getter
    public static class response {
        private Long id;
        private String username;
        private String contents;
        private String createAt;
        private Integer depth;
        private Boolean enabled;
        private Boolean isMine;

        public response(Comment comment, String currentUserId) {
            this.id = comment.getId();
            this.username = comment.getUser().getUsername();
            this.contents = MarkdownUtil.line2br(MarkdownUtil.escape(comment.getContents()));
            this.depth = comment.getDepth();
            this.createAt = FormatUtil.dateTimeToBeautifulDate(comment.getCreateAt());
            this.enabled = comment.getEnabled();
            this.isMine = comment.getCreateBy().equalsIgnoreCase(currentUserId);
        }

    }
}
