package com.cglee079.pododev.core.global.response;

import lombok.Builder;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ListResponse extends ApiResponse {

    private Map<String, Object> result;

    @Builder
    public ListResponse(ApiStatus status, List<?> results) {
        super(status);

        result = new LinkedHashMap<>();

        this.result.put("size", results.size());
        this.result.put("data", results);
    }
}
