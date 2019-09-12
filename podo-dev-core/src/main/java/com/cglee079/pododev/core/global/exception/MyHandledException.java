package com.cglee079.pododev.core.global.exception;

import com.cglee079.pododev.core.global.response.ApiStatus;
import org.springframework.http.HttpStatus;

public abstract class MyHandledException extends RuntimeException {

    public MyHandledException(String message) {
        super(message);
    }

    abstract public HttpStatus getHttpStatus();
    abstract public ApiStatus getApiStatus();
}
