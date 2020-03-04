package com.podo.pododev.web.domain.user.exception;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import org.springframework.http.HttpStatus;

public class NoAuthenticatedApiException extends ApiException {

    public NoAuthenticatedApiException() {
        super("인증되지 않은 사용자입니다.");
    }

    @Override
    public String getField() {
        return "";
    }

    @Override
    public Object getValue() {
        return "";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public DefaultApiStatus getApiStatus() {
        return DefaultApiStatus.ERR_INVALID;
    }
}
