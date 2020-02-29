package com.podo.pododev.core.rest;

import com.podo.pododev.core.rest.status.DefaultApiStatus;
import lombok.Getter;

@Getter
public abstract class ApiResponse {

    private String code;
    private String message;

    public ApiResponse(DefaultApiStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }
}
