package com.podo.pododev.web.domain.blog.comment.dto;

import com.podo.pododev.core.util.DateTimeFormatUtil;
import com.podo.pododev.web.domain.blog.comment.model.Comment;
import com.podo.pododev.web.domain.blog.comment.dto.value.Writer;
import com.podo.pododev.web.domain.user.model.User;
import com.podo.pododev.web.global.util.HtmlDocumentUtil;
import lombok.Getter;

@Getter
public class CommentResponse {

    private Long id;
    private String contents;
    private String createAt;
    private Integer depth;
    private Boolean enabled;
    private Boolean isMine;
    private Writer writer;

    public CommentResponse(Comment comment, Long userId) {
        final User writer = comment.getWriter();

        this.id = comment.getId();
        this.contents = HtmlDocumentUtil.line2br(HtmlDocumentUtil.escapeHtml(comment.getContents()));
        this.depth = comment.getDepth();
        this.createAt = DateTimeFormatUtil.dateTimeToBeautifulDate(comment.getCreateAt());
        this.enabled = comment.getEnabled();
        this.isMine = writer.getId().equals(userId);
        this.writer = new Writer(writer);
    }
}
