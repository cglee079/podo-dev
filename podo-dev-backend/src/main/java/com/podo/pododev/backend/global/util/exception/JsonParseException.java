package com.podo.pododev.backend.global.util.exception;

public class JsonParseException extends RuntimeException {
    public <T> JsonParseException(Class<T> clazz, Exception e) {
        super("JSON parse에 실패했습니다 " + clazz.getName(), e);
    }
}
