package com.podo.pododev.core.rest.response;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StatusResponse extends ApiResponse {

    @Builder
    public StatusResponse(DefaultApiStatus status) {
        super(status);
    }

    public static StatusResponse success() {
        return StatusResponse.builder().status(DefaultApiStatus.SUCCESS).build();
    }
}
