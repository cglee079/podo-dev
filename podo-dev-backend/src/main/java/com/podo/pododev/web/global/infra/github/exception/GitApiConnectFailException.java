package com.podo.pododev.web.global.infra.github.exception;

public class GitApiConnectFailException extends RuntimeException {

    public GitApiConnectFailException(Exception e) {
        super("Github Api 접속에 실패하였습니다", e);
    }
}
