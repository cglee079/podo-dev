package com.podo.pododev.web.global.config.aop.argschecker;

public class NotAllArgsNullException extends RuntimeException{

    public NotAllArgsNullException(String string) {
        super("Args에 null 인 값이 있습니다 :  " + string);
    }
}
