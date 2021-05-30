package com.podo.pododev.backend.domain.blog.attach.attachimage.exception;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import org.springframework.http.HttpStatus;

public class InvalidImageFileApiException extends ApiException {

    private String filename;

    public InvalidImageFileApiException(String filename) {
        super("유효하지 않은 이미지입니다 : " + filename);

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
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public DefaultApiStatus getApiStatus() {
        return DefaultApiStatus.ERR_INVALID;
    }
}
