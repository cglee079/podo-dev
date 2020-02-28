package com.podo.pododev.core.rest.response;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.*;

@Getter
public class CollectionResponse extends ApiResponse {

    private Map<String, Object> result;

    @Builder
    public CollectionResponse(DefaultApiStatus status, Collection<?> result) {
        super(status);

        this.result = new LinkedHashMap<>();

        this.result.put("size", result.size());
        this.result.put("contents", result);
    }

    @Builder(builderClassName = "SuccessBuilder", builderMethodName = "success")
    public CollectionResponse(Collection<?> result) {
        this(DefaultApiStatus.SUCCESS, result);
    }
}
