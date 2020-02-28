package com.podo.pododev.web.domain.blog.attachimage.exception;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import org.springframework.http.HttpStatus;

public class InvalidImageUrlApiException extends ApiException {

    private String imageUrl;

    public InvalidImageUrlApiException(String imageUrl) {
        super("유효하지 않은 이미지입니다 : " + imageUrl);
        this.imageUrl = imageUrl;
    }

    @Override
    public String getField() {
        return "imageUrl";
    }

    @Override
    public Object getValue() {
        return imageUrl;
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
