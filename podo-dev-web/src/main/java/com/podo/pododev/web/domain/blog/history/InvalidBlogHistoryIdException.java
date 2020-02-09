package com.podo.pododev.web.domain.blog.history;

import com.podo.pododev.core.rest.exception.ResponsibleException;
import com.podo.pododev.core.rest.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class InvalidBlogHistoryIdException extends ResponsibleException {

    public InvalidBlogHistoryIdException(Long blogHistoryId) {
        super("유효하지 않은 블로그 History ID 입니다. ID : " + blogHistoryId);
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