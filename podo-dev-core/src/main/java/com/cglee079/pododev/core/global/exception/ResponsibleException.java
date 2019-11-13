package com.cglee079.pododev.core.global.exception;

import com.cglee079.pododev.core.global.response.ApiStatus;
import org.springframework.http.HttpStatus;

public abstract class ResponsibleException extends RuntimeException {

    public ResponsibleException(String message) {
        super(message);
    }

    abstract public HttpStatus getHttpStatus();
    abstract public ApiStatus getApiStatus();
}
