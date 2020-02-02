package com.podo.pododev.web.domain.blog.exception;

import com.podo.pododev.core.rest.exception.ResponsibleException;
import com.podo.pododev.core.rest.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class PublishNotYetException extends ResponsibleException {

    public PublishNotYetException() {
        super("아직 발행전 게시글입니다, 먼저 발행해주세요");
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
