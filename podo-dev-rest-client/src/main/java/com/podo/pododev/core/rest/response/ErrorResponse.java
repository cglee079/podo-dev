package com.podo.pododev.core.rest.response;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.response.dto.ErrorDto;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class ErrorResponse implements ApiResponse {

    private List<ErrorDto> errors;
    private String code;
    private String message;

    @Builder(builderMethodName = "multiError", builderClassName = "MultiError")
    public ErrorResponse(DefaultApiStatus status, List<ErrorDto> errors) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.errors = errors;
    }

    @Builder(builderMethodName = "singleError", builderClassName = "SingleError")
    public ErrorResponse(DefaultApiStatus status, ErrorDto error) {
        new ErrorResponse(status, Collections.singletonList(error));
    }
}
