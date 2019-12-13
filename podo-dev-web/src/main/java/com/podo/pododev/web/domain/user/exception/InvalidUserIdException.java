package com.podo.pododev.web.domain.user.exception;

import com.podo.pododev.core.rest.exception.ResponsibleException;
import com.podo.pododev.core.rest.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class InvalidUserIdException extends ResponsibleException {

    public InvalidUserIdException(String userId) {
        super("유효하지 않은 사용자ID 입니다. ID : " + userId);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public ApiStatus getApiStatus() {
        return ApiStatus.ERR_INVALID;
    }
}
