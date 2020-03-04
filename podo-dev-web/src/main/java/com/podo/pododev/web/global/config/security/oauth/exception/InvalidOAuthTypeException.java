package com.podo.pododev.web.global.config.security.oauth.exception;

public class InvalidOAuthTypeException extends RuntimeException {

    public InvalidOAuthTypeException(String id) {
        super("알수 없는 OAuth ID 입니다 : " + id);

    }
}
