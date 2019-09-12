package com.cglee079.pododev.web.global.infra.uploader.exception;

import com.cglee079.pododev.core.global.exception.MyHandledException;
import com.cglee079.pododev.core.global.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class UploadFailException extends MyHandledException {
    public UploadFailException() {
        super("이미지 업로드에 실패하였습니다");
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public ApiStatus getApiStatus() {
        return ApiStatus.ERR_UPLOAD;
    }
}
