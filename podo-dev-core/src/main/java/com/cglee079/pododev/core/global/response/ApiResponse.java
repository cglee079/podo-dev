package com.cglee079.pododev.core.global.response;

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
