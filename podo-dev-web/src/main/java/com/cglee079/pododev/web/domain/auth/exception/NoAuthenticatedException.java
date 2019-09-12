package com.cglee079.pododev.web.domain.auth.exception;

import com.cglee079.pododev.core.global.exception.MyHandledException;
import com.cglee079.pododev.core.global.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class NoAuthenticatedException extends MyHandledException {

    public NoAuthenticatedException() {
        super("인증되지 않은 사용자입니다.");
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public ApiStatus getApiStatus() {
        return ApiStatus.ERR_NOT_VALID;
    }
}
