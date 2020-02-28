package com.podo.pododev.web.global.infra.telegram.exception;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import org.springframework.http.HttpStatus;

public class TelegramException extends ApiException {
    public TelegramException(Exception e) {
        super("텔레그램 서버 에러가 발생하였습니다");
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public DefaultApiStatus getApiStatus() {
        return DefaultApiStatus.ERR_TELEGRAM_SERVER;
    }
}
