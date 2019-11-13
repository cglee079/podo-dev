package com.cglee079.pododev.web.domain.blog.comment.exception;

import com.cglee079.pododev.core.global.exception.ResponsibleException;
import com.cglee079.pododev.core.global.response.ApiStatus;
import org.springframework.http.HttpStatus;

public class MaxDepthCommentException extends ResponsibleException {

    public MaxDepthCommentException() {
        super("더 이상 답글을 달 수 없습니다 (답글 깊이 제한)");
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public ApiStatus getApiStatus() {
        return ApiStatus.ERR_NOT_VALID;
    }
}
