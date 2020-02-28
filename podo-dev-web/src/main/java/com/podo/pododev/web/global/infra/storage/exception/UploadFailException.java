package com.podo.pododev.web.global.infra.storage.exception;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import org.springframework.http.HttpStatus;

public class UploadFailException extends ApiException {
    public UploadFailException(String message) {
        super(String.format("이미지 업로드에 실패하였습니다 '%s'", message));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public DefaultApiStatus getApiStatus() {
        return DefaultApiStatus.ERR_STORAGE_SERVER;
    }
}
