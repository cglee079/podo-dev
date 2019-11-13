package com.podo.pododev.core.rest.response;

import lombok.Getter;

@Getter
public abstract class ApiResponse {

    String code;
    String message;

    public ApiResponse(ApiStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }
}
