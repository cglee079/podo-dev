package com.podo.pododev.core.rest.exception;

import com.podo.pododev.core.rest.response.ApiStatus;
import org.springframework.http.HttpStatus;

public abstract class ResponsibleException extends RuntimeException {

    public ResponsibleException(String message) {
        super(message);
    }

    abstract public HttpStatus getHttpStatus();
    abstract public ApiStatus getApiStatus();
}
