package com.cglee079.pododev.global.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StatusResponse extends ApiResponse {


    @Builder
    public StatusResponse(ResponseStatus status){
        super(status);
    }
}
