package com.cglee079.pododev.web.domain.blog.comment.exception;

import com.cglee079.pododev.core.global.exception.MyHandledException;
import com.cglee079.pododev.core.global.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class InvalidCommentException extends MyHandledException {

    public InvalidCommentException() {
        super("유효하지 않은 코멘트 입니다");
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public ApiStatus getApiStatus() {
        return ApiStatus.ERR_NOT_VALID;
    }
}
