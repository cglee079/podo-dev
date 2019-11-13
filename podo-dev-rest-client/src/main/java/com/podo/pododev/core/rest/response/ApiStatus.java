package com.podo.pododev.core.rest.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApiStatus {

    SUCCESS("000", "success"),

    ERR_INVALID("400", "Not Valid Input"),
    ERR_ID_DUPLICATE("401", "Id duplicate"),

    ERR_SERVER_ERROR("500", "Server Error"),
    ERR_UPLOAD("510", "Upload Server Error"),
    ERR_SOLR("511", "Solr Error"),
    ERR_TELEGRAM("512", "Telegram Error"),
    ERR_SAVE("513", "Save File to Server Error");

    private final String code;
    private final String message;


}
