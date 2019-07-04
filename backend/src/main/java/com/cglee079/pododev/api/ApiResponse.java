package com.cglee079.pododev.api;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse {
    String code;
    String message;
    Object data;
}
