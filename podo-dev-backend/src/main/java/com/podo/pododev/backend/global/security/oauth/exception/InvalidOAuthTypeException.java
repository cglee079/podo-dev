package com.podo.pododev.backend.global.security.oauth.exception;

public class InvalidOAuthTypeException extends RuntimeException {

    public InvalidOAuthTypeException(String id) {
        super("알수 없는 OAuth ID 입니다 : " + id);

    }
}
