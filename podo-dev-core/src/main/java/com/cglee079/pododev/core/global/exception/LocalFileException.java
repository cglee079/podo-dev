package com.cglee079.pododev.core.global.exception;

import com.cglee079.pododev.core.global.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class LocalFileException extends ResponsibleException {
    public LocalFileException(Exception e) {
        super("파일 처리에 실패하였습니다");
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
