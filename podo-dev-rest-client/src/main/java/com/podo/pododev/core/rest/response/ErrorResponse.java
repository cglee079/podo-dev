package com.podo.pododev.core.rest.response;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.response.dto.ErrorDto;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class ErrorResponse extends ApiResponse {

    private List<ErrorDto> errors;

    @Builder(builderMethodName = "multiError", builderClassName = "MultiError")
    public ErrorResponse(DefaultApiStatus status, List<ErrorDto> errors) {
        super(status);
        this.errors = errors;
    }

    @Builder(builderMethodName = "singleError", builderClassName = "SingleError")
    public ErrorResponse(DefaultApiStatus status, ErrorDto error) {
        super(status);
        this.errors = Collections.singletonList(error);
    }
}
