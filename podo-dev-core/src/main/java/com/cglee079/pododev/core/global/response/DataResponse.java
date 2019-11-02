package com.cglee079.pododev.core.global.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DataResponse extends ApiResponse {

    Object data;

    @Builder
    public DataResponse(ApiStatus status, Object data){
        super(status);
        this.data = data;
    }
}
