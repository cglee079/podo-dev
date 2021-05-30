package com.podo.pododev.backend.domain.blog.history.exception;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import org.springframework.http.HttpStatus;

public class InvalidBlogHistoryIdApiException extends ApiException {

    private Long blogHistoryId;

    public InvalidBlogHistoryIdApiException(Long blogHistoryId) {
        super("유효하지 않은 블로그 History ID 입니다. ID : " + blogHistoryId);

        this.blogHistoryId = blogHistoryId;
    }

    @Override
    public String getField() {
        return "blogHistoryId";
    }

    @Override
    public Object getValue() {
        return blogHistoryId;
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
