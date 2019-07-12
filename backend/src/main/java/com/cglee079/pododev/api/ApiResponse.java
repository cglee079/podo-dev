package com.cglee079.pododev.api;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse {

    @Builder.Default
    String code = "000";

    @Builder.Default
    String message = "success";

    Object data;
}
