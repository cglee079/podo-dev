package com.podo.pododev.backend.domain.blog.blog.exception;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import org.springframework.http.HttpStatus;

public class InvalidBlogIdApiException extends ApiException {

    private Long blogId;

    public InvalidBlogIdApiException(Long blogId) {
        super("유효하지 않은 블로그 ID 입니다. ID : " + blogId);

        this.blogId = blogId;
    }

    @Override
    public String getField() {
        return "blogId";
    }

    @Override
    public Object getValue() {
        return blogId;
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
