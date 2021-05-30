package com.podo.pododev.backend.global.util.exception;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import org.springframework.http.HttpStatus;

public class FileProcessFailApiException extends ApiException {

    private String path;

    public FileProcessFailApiException(String path) {
        super("파일 처리에 실패하였습니다 : " + path);

        this.path = path;
    }

    @Override
    public String getField() {
        return "file";
    }

    @Override
    public Object getValue() {
        return path;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public DefaultApiStatus getApiStatus() {
        return DefaultApiStatus.ERR_FILE_PROCESS;
    }
}
