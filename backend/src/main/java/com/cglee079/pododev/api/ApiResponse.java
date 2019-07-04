package com.cglee079.pododev.api;

import lombok.Builder;

@Builder
public class ApiResponse {
    String code;
    String message;
    Object data;
}
