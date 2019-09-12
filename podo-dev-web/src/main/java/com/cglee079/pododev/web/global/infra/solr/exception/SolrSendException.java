package com.cglee079.pododev.web.global.infra.solr.exception;

import com.cglee079.pododev.core.global.exception.MyHandledException;
import com.cglee079.pododev.core.global.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class SolrSendException extends MyHandledException {
    public SolrSendException(Exception e) {
        //Solr
        super("서버 에러가 발생하였습니다");
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public ApiStatus getApiStatus() {
        return ApiStatus.ERR_SOLR;
    }
}
