package com.podo.pododev.core.rest.response;

import lombok.Builder;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ListResponse extends ApiResponse {

    private Map<String, Object> result;

    @Builder
    public ListResponse(ApiStatus status, List<?> result) {
        super(status);

        result = new LinkedHashMap<>();

        this.result.put("size", result.size());
        this.result.put("data", result);
    }
}
