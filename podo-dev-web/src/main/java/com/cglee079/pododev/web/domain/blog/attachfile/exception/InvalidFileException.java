package com.cglee079.pododev.web.domain.blog.attachfile.exception;

import com.cglee079.pododev.core.global.exception.HandledException;
import com.cglee079.pododev.core.global.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class InvalidFileException extends HandledException {

    public InvalidFileException() {
        super("유효하지 않은 파일입니다");
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
