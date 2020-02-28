package com.podo.pododev.web.domain.blog.comment;

import com.podo.pododev.web.global.util.HtmlDocumentUtil;
import com.podo.pododev.web.domain.blog.blog.Blog;
import com.podo.pododev.core.util.DateTimeFormatUtil;
import com.podo.pododev.web.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

public class CommentDto {


    @Setter
    @Getter
    public static class requestPaging {
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
            this.username = comment.getWriter().getUsername();
            this.contents = comment.getContents().replace("\n", " ");
            this.createAt = DateTimeFormatUtil.dateTimeToBeautifulDate(comment.getCreateAt());
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

        public response(Comment comment, String userId) {
            final User writeBy = comment.getWriter();

            this.id = comment.getId();
            this.username = writeBy.getUsername();
            this.contents = HtmlDocumentUtil.line2br(HtmlDocumentUtil.escapeHtml(comment.getContents()));
            this.depth = comment.getDepth();
            this.createAt = DateTimeFormatUtil.dateTimeToBeautifulDate(comment.getCreateAt());
            this.enabled = comment.getEnabled();
            this.isMine = writeBy.getUserKey().equalsIgnoreCase(userId);
        }

    }
}
