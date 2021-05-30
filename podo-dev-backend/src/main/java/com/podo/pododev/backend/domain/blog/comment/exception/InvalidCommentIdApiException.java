package com.podo.pododev.backend.domain.blog.comment.exception;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import org.springframework.http.HttpStatus;

public class InvalidCommentIdApiException extends ApiException {

    private Long commentId;

    public InvalidCommentIdApiException(Long commentId) {
        super("유효하지 않은 댓글 ID 입니다. ID : " + commentId);

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
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public DefaultApiStatus getApiStatus() {
        return DefaultApiStatus.ERR_INVALID;
    }
}
