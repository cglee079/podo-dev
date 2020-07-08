package com.podo.pododev.web.domain.blog.comment.dto;

import com.podo.pododev.core.util.DateTimeFormatUtil;
import com.podo.pododev.web.domain.blog.blog.model.Blog;
import com.podo.pododev.web.domain.blog.comment.model.Comment;
import com.podo.pododev.web.domain.blog.comment.dto.value.Writer;
import com.podo.pododev.web.domain.user.model.User;
import lombok.Getter;

@Getter
public class CommentSummary {

    private Long id;
    private Long blogId;
    private String blogTitle;
    private String contents;
    private String createAt;
    private Writer writer;

    public CommentSummary(Comment comment) {
        final Blog blog = comment.getBlog();
        final User writer = comment.getWriter();

        this.id = comment.getId();
        this.blogId = blog.getId();
        this.blogTitle = blog.getTitle();
        this.contents = comment.getContents().replace("\n", " ");
        this.createAt = DateTimeFormatUtil.dateTimeToBeautifulDate(comment.getCreateAt());
        this.writer = new Writer(writer);
    }
}
