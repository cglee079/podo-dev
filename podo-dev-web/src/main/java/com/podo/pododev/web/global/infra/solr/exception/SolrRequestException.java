package com.podo.pododev.web.global.infra.solr.exception;

import com.podo.pododev.core.rest.exception.ResponsibleException;
import com.podo.pododev.core.rest.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class SolrRequestException extends ResponsibleException {

    public SolrRequestException(Exception e) {
        super(String.format("검색서버 에러가 발생하였습니다 '%s'", e.getMessage()));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public ApiStatus getApiStatus() {
        return ApiStatus.ERR_SOLR_SERVER;
    }
}
