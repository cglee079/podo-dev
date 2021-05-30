package com.podo.pododev.backend.global.aop.exception;

public class CantFindArgumentException extends RuntimeException {
    public CantFindArgumentException(Class<?> clazz) {
        super("찾을 수 없는 인자 값입니다 :" + clazz.getName());
    }
}
