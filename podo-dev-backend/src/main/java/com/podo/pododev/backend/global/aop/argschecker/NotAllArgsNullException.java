package com.podo.pododev.backend.global.aop.argschecker;

public class NotAllArgsNullException extends RuntimeException{

    public NotAllArgsNullException(String string) {
        super("Args에 null 인 값이 있습니다 :  " + string);
    }
}
