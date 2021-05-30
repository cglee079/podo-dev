package com.podo.pododev.backend.global.external.git.exception;

public class GitApiConnectFailException extends RuntimeException {

    public GitApiConnectFailException(Exception e) {
        super("Github Api 접속에 실패하였습니다", e);
    }
}
