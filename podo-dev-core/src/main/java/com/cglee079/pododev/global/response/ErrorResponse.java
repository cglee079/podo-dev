package com.cglee079.pododev.global.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse extends ApiResponse {

    String[] errors;

    @Builder(builderMethodName = "multiError", builderClassName = "multiError")
    public ErrorResponse(ResponseStatus status, String[] errors) {
        super(status);
        this.errors = errors;
    }

    @Builder(builderMethodName = "singleError", builderClassName = "singleError")
    public ErrorResponse(ResponseStatus status, String error) {
        super(status);
        this.errors = new String[]{error};
    }
}
