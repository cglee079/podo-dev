package com.cglee079.pododev.core.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiStatus {

    SUCCESS("0000", "message");

    private final String code;
    private final String message;
}
