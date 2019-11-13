package com.cglee079.pododev.web.global.handler;

import com.cglee079.pododev.core.global.exception.ResponsibleException;
import com.cglee079.pododev.core.global.response.ApiStatus;
import com.cglee079.pododev.core.global.response.ErrorsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            ResponsibleException.class
    })
    public ResponseEntity handleException(ResponsibleException e) {

        ErrorsResponse response = ErrorsResponse.singleError()
                .status(e.getApiStatus())
                .error(e.getMessage())
                .build();

        return ResponseEntity.status(e.getHttpStatus()).body(response);
    }

    /**
     * Dto Insert, Update Validation Error
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        BindingResult result = e.getBindingResult();
        List<String> fieldErrors = result.getFieldErrors().stream()
                .map(f -> f.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorsResponse response = ErrorsResponse.multiError()
                .status(ApiStatus.ERR_NOT_VALID)
                .errors(fieldErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
