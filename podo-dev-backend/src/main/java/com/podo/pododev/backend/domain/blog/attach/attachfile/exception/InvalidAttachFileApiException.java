package com.podo.pododev.backend.domain.blog.attach.attachfile.exception;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import org.springframework.http.HttpStatus;

public class InvalidAttachFileApiException extends ApiException {

    private Long attachFileId;

    public InvalidAttachFileApiException(Long attachFileId) {
        super("유효하지 않은 첨부파일입니다");

        this.attachFileId = attachFileId;
    }

    @Override
    public String getField() {
        return "attachFileId";
    }

    @Override
    public Object getValue() {
        return attachFileId;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public DefaultApiStatus getApiStatus() {
        return DefaultApiStatus.ERR_INVALID;
    }
}
