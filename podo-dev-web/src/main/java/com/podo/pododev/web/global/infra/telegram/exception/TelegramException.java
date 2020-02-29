package com.podo.pododev.web.global.infra.telegram.exception;

public class TelegramException extends RuntimeException {
    public TelegramException(Exception e) {
        super("텔레그램 서버 에러가 발생하였습니다", e);
    }

}
