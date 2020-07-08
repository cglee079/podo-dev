package com.podo.pododev.web.domain.blog.comment.exception;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import org.springframework.http.HttpStatus;

public class MaxDepthCommentApiException extends ApiException {

    private Long commentId;

    public MaxDepthCommentApiException(Long commentId) {
        super("더 이상 답글을 달 수 없습니다.");

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
