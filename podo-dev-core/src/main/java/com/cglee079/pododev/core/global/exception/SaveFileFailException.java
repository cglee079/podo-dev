package com.cglee079.pododev.core.global.exception;

import com.cglee079.pododev.core.global.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class SaveFileFailException extends MyHandledException {
    public SaveFileFailException(Exception e) {
        super("파일을 저장 할 수 없습니다");
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public ApiStatus getApiStatus() {
        return ApiStatus.ERR_SAVE;
    }
}
