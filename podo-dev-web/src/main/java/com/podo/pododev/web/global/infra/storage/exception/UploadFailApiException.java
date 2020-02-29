package com.podo.pododev.web.global.infra.storage.exception;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import org.springframework.http.HttpStatus;

public class UploadFailApiException extends ApiException {

    private String filename;

    public UploadFailApiException(String filename) {
        super(String.format("이미지 업로드에 실패하였습니다 '%s'", filename));
        this.filename = filename;
    }

    @Override
    public String getField() {
        return "imageFile";
    }

    @Override
    public Object getValue() {
        return filename;
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
