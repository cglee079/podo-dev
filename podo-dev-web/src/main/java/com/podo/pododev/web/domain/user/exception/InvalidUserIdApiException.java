package com.podo.pododev.web.domain.user.exception;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import org.springframework.http.HttpStatus;

public class InvalidUserIdApiException extends ApiException {

    public InvalidUserIdApiException(Long userId) {
        super("유효하지 않은 사용자 ID 입니다. ID : " + userId);
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
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public DefaultApiStatus getApiStatus() {
        return DefaultApiStatus.ERR_INVALID;
    }
}
