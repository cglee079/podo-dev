package com.cglee079.pododev.web.global.exception;

import com.cglee079.pododev.core.global.exception.HandledException;
import com.cglee079.pododev.core.global.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class FailImageResizeException extends HandledException {

    public FailImageResizeException() {
        super("이미지 리사이징에 실패했습니다");
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public ApiStatus getApiStatus() {
        return ApiStatus.ERR_SERVER_ERROR;
    }
}
