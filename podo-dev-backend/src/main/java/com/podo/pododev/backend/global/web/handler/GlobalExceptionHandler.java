package com.podo.pododev.backend.global.web.handler;

import com.podo.pododev.core.rest.ApiException;
import com.podo.pododev.core.rest.response.ErrorResponse;
import com.podo.pododev.core.rest.response.dto.ErrorDto;
import com.podo.pododev.core.rest.status.DefaultApiStatus;
import io.sentry.Sentry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final List<Class<?>> NO_REPORT_EXCEPTION = Arrays.asList(
            CannotCreateTransactionException.class, ApiException.class
    );

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {

        if(NO_REPORT_EXCEPTION.stream().noneMatch(ex -> ex.isInstance(e))){
            Sentry.captureException(e);
        }

        final ErrorResponse response = ErrorResponse.singleError()
                .status(DefaultApiStatus.ERR_SERVER_ERROR)
                .error(new ErrorDto("", "", "죄송합니다. 알 수 없는 예외가 발생했습니다."))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleException(ApiException e) {
        final ErrorResponse response = ErrorResponse.singleError()
                .status(e.getApiStatus())
                .error(new ErrorDto(e.getField(), e.getValue(), e.getMessage()))
                .build();

        return ResponseEntity.status(e.getHttpStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleBindingValidException(MethodArgumentNotValidException e) {
        final BindingResult result = e.getBindingResult();

        final List<ErrorDto> fieldErrors = result.getFieldErrors().stream()
                .map(fe -> new ErrorDto(fe.getField(), fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        final ErrorResponse response = ErrorResponse.multiError()
                .status(DefaultApiStatus.ERR_INVALID)
                .errors(fieldErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        final BindingResult result = e.getBindingResult();
        final List<ErrorDto> fieldErrors = result.getFieldErrors().stream()
                .map(fe -> new ErrorDto(fe.getField(), fe.getRejectedValue(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        final ErrorResponse response = ErrorResponse.multiError()
                .status(DefaultApiStatus.ERR_INVALID)
                .errors(fieldErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
