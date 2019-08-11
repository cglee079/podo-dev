package com.cglee079.pododev.web.core.global.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StatusResponse extends ApiResponse {


    @Builder
    public StatusResponse(ResponseStatus status){
        super(status);
    }
}
