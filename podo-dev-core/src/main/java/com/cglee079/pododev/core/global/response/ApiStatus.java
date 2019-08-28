package com.cglee079.pododev.core.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApiStatus {

    SUCCESS("000", "success"),

    ERR_ID_DUPLICATE("401", "Id duplicate"),
    ERR_NOT_VALID("402", "Not Valid Input");

    private final String code;
    private final String message;




}
