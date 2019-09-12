package com.cglee079.pododev.web.domain.blog.attachimage.exception;

import com.cglee079.pododev.core.global.exception.MyHandledException;
import com.cglee079.pododev.core.global.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class InValidImageException extends MyHandledException {
    public InValidImageException() {
        super("유효하지 않은 이미지입니다");
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
