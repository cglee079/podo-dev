package com.podo.pododev.core.rest.response;

import lombok.Builder;
import lombok.Getter;

import java.util.*;

@Getter
public class CollectionResponse extends ApiResponse {

    private Map<String, Object> result;

    @Builder
    public CollectionResponse(ApiStatus status, Collection<?> result) {
        super(status);

        this.result = new LinkedHashMap<>();

        this.result.put("size", result.size());
        this.result.put("contents", result);
    }
}
