package com.podo.pododev.core.rest.response;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DataResponse extends ApiResponse {

    private Object result;

    @Builder(builderClassName = "successBuilder", builderMethodName = "success")
    public DataResponse(Object result){
        this(DefaultApiStatus.SUCCESS, result);
    }

    @Builder
    public DataResponse(DefaultApiStatus status, Object result) {
        super(status);
        this.result = result;
    }
}
