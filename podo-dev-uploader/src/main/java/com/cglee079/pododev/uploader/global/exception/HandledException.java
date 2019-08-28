package com.cglee079.pododev.uploader.global.exception;

import com.cglee079.pododev.core.global.response.ApiStatus;
import org.springframework.http.HttpStatus;

public abstract class HandledException extends RuntimeException {

    public HandledException(String message) {
        super(message);
    }

    abstract public HttpStatus getHttpStatus();
    abstract public ApiStatus getResponseStatus();
}
