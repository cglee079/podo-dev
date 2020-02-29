package com.podo.pododev.core.rest.response;

import com.podo.pododev.core.rest.ApiResponse;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

        if(result instanceof Collection){
            this.result = toMap((Collection) result);
            return;
        }
        this.result = result;
    }

    private Map<String, Object> toMap(Collection result) {
        final HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("size", result.size());
        resultMap.put("contents", result);

        return resultMap;
    }
}
