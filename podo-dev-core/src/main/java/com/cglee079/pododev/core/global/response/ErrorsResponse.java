package com.cglee079.pododev.core.global.response;

import lombok.Builder;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class ErrorsResponse extends ApiResponse {

    List<String> errors;

    @Builder(builderMethodName = "multiError", builderClassName = "multiError")
    public ErrorsResponse(ApiStatus status, List<String> errors) {
        super(status);
        this.errors = errors;
    }

    @Builder(builderMethodName = "singleError", builderClassName = "singleError")
    public ErrorsResponse(ApiStatus status, String error) {
        super(status);
        this.errors = new LinkedList<>();
        this.errors.add(error);
    }
}
