package com.podo.pododev.web.global.infra.solr.exception;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import org.springframework.http.HttpStatus;

public class SolrRequestException extends ApiException {

    public SolrRequestException(Exception e) {
        super(String.format("검색서버 에러가 발생하였습니다 '%s'", e.getMessage()));
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
