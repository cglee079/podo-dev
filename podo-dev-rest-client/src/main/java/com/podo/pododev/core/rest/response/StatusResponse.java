package com.podo.pododev.core.rest.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StatusResponse extends ApiResponse {


    @Builder
    public StatusResponse(ApiStatus status){
        super(status);
    }
}
