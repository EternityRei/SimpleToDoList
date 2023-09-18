package com.todo.list.exception;

import com.todo.list.dto.RemoteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public RemoteResponse notFoundException(BaseException ex) {
        log.error("The error occur with message={}", ex.getMessage());
        return RemoteResponse.create(false, ex.getErrorCode(), ex.getMessage(), null);
    }
}
