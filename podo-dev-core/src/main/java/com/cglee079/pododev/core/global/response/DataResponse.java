package com.cglee079.pododev.core.global.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DataResponse extends ApiResponse {

    Object result;

    @Builder
    public DataResponse(ApiStatus status, Object result){
        super(status);
        this.result = result;
    }
}
