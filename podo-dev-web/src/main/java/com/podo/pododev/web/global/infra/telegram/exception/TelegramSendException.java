package com.podo.pododev.web.global.infra.telegram.exception;

import com.podo.pododev.core.rest.exception.ResponsibleException;
import com.podo.pododev.core.rest.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class TelegramSendException extends ResponsibleException {
    public TelegramSendException(Exception e) {
        super("서버 에러가 발생하였습니다");
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public ApiStatus getApiStatus() {
        return ApiStatus.ERR_TELEGRAM;
    }
}
