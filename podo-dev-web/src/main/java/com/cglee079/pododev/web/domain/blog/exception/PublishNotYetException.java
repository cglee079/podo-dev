package com.cglee079.pododev.web.domain.blog.exception;

import com.cglee079.pododev.core.global.exception.HandledException;
import com.cglee079.pododev.core.global.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class PublishNotYetException extends HandledException {

    public PublishNotYetException() {
        super("아직 발행전 게시글입니다, 먼저 발행해주세요");
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
