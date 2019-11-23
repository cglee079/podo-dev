package com.podo.pododev.web.domain.blog.attachfile.exception;

import com.podo.pododev.core.rest.exception.ResponsibleException;
import com.podo.pododev.core.rest.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class InvalidAttachFileException extends ResponsibleException {

    public InvalidAttachFileException() {
        super("유효하지 않은 첨부파일입니다");
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public ApiStatus getApiStatus() {
        return ApiStatus.ERR_INVALID;
    }
}
