package com.cglee079.pododev.domain.api;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponse {

    String code;
    String message;
    Object data;

    @Builder
    public ApiResponse(ApiStatus status, Object data){
        this.code = status.getCode();
        this.message = status.getMessage();
        this.data = data;
    }
}
