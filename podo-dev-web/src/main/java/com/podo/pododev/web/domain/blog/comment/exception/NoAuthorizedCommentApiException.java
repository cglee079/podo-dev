package com.podo.pododev.web.domain.blog.comment.exception;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import org.springframework.http.HttpStatus;

public class NoAuthorizedCommentApiException extends ApiException {

    private Long commentId;

    public NoAuthorizedCommentApiException(Long commentId) {
        super("권한이 없는 댓글 입니다 : " + commentId);
        this.commentId = commentId;
    }

    @Override
    public String getField() {
        return "commentId";
    }

    @Override
    public Object getValue() {
        return commentId;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public DefaultApiStatus getApiStatus() {
        return DefaultApiStatus.UNAUTHORIZED;
    }
}
