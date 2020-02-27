package com.podo.pododev.web.domain.blog.blog.exception;

import com.podo.pododev.core.rest.exception.ResponsibleException;
import com.podo.pododev.core.rest.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class InvalidBlogIdException extends ResponsibleException {

    public InvalidBlogIdException(Long blogId) {
        super("유효하지 않은 블로그 ID 입니다. ID : " + blogId);
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
