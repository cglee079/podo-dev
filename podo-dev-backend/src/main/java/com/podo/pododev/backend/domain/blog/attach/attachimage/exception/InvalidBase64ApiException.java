package com.podo.pododev.backend.domain.blog.attach.attachimage.exception;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import org.springframework.http.HttpStatus;

public class InvalidBase64ApiException extends ApiException {

    private String base64;

    public InvalidBase64ApiException(String base64) {
        super("유효하지 않은 이미지입니다 : " + base64);

        this.base64 = base64;
    }

    @Override
    public String getField() {
        return "base64";
    }

    @Override
    public Object getValue() {
        return base64;
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
