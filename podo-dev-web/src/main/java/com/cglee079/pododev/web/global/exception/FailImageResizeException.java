package com.cglee079.pododev.web.global.exception;

import com.cglee079.pododev.core.global.exception.ResponsibleException;
import com.cglee079.pododev.core.global.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class FailImageResizeException extends ResponsibleException {

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
