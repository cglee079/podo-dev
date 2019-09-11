package com.cglee079.pododev.core.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApiStatus {

    SUCCESS("000", "success"),

    ERR_ID_DUPLICATE("401", "Id duplicate"),
    ERR_NOT_VALID("402", "Not Valid Input"),

    ERR_UPLOAD("510", "Upload Server Error"),
    ERR_SOLR("511", "Solr Error"),
    ERR_TELEGRAM("512", "Telegram Error");

    private final String code;
    private final String message;


}
