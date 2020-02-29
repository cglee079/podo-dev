package com.podo.pododev.web.global.infra.solr.exception;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import org.springframework.http.HttpStatus;

public class SearchWordApiException extends ApiException {

    public SearchWordApiException(String keyword) {
        super(String.format("검색 단어 조회에 실패였습니다 '%s'", keyword));
    }

    @Override
    public String getField() {
        return "";
    }

    @Override
    public Object getValue() {
        return "";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public DefaultApiStatus getApiStatus() {
        return DefaultApiStatus.ERR_SOLR_SERVER;
    }
}
