package com.cglee079.pododev.web.domain.blog.exception;

import com.cglee079.pododev.core.global.exception.ResponsibleException;
import com.cglee079.pododev.core.global.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class InvalidBlogIdException extends ResponsibleException {

    public InvalidBlogIdException() {
        super("유효하지 않은 블로그 ID 입니다");
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
