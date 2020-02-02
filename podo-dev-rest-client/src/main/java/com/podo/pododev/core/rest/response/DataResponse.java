package com.podo.pododev.core.rest.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DataResponse extends ApiResponse {

    private Object result;

    @Builder
    public DataResponse(ApiStatus status, Object result) {
        super(status);
        this.result = result;
    }
}
