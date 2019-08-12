package com.cglee079.pododev.core.global.response;

import lombok.Getter;

@Getter
public class ApiResponse {

    String code;
    String message;

    public ApiResponse(ResponseStatus status){
        this.code = status.getCode();
        this.message = status.getMessage();
    }
}
