package com.podo.pododev.core.rest.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApiStatus {

    SUCCESS("000", "success"),

    ERR_INVALID("400", "유효하지 않은 값입니다"),
    ERR_ID_DUPLICATE("401", "중복되는 값입니다"),

    ERR_SERVER_ERROR("500", "서버 에러"),
    ERR_UPLOAD("510", "업로드 서버 에러"),
    ERR_SOLR("520", "검색 서버 에러"),
    ERR_TELEGRAM("512", "텔레그램 서버 에러"),
    ERR_SAVE("513", "저장 서버 에러");

    private final String code;
    private final String message;


}
