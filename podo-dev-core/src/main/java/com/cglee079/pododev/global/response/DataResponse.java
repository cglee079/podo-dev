package com.cglee079.pododev.global.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DataResponse extends ApiResponse {

    Object data;

    @Builder
    public DataResponse(ResponseStatus status, Object data){
        super(status);
        this.data = data;
    }
}
