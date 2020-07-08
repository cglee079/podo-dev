package com.podo.pododev.web.global.infra.solr.exception;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import org.springframework.http.HttpStatus;

public class SearchApiException extends ApiException {

    private String searchValue;

    public SearchApiException(String searchValue) {
        super(String.format("검색 에러 발생하였습니다 '%s'", searchValue));
        this.searchValue = searchValue;
    }

    @Override
    public String getField() {
        return "searchValue";
    }

    @Override
    public Object getValue() {
        return searchValue;
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
