package com.podo.pododev.web.global.infra.storage.exception;

import com.podo.pododev.core.rest.exception.ResponsibleException;
import com.podo.pododev.core.rest.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class UploadFailException extends ResponsibleException {
    public UploadFailException(String message) {
        super(String.format("이미지 업로드에 실패하였습니다 '%s'", message));
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
