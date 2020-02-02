package com.podo.pododev.web.domain.blog.comment.exception;

import com.podo.pododev.core.rest.exception.ResponsibleException;
import com.podo.pododev.core.rest.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class InvalidCommentIdException extends ResponsibleException {

    public InvalidCommentIdException(Long commentId) {
        super("유효하지 않은 댓글 ID 입니다. ID : " + commentId);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public ApiStatus getApiStatus() {
        return ApiStatus.ERR_INVALID;
    }
}
