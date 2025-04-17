package com.stockservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404
    @ExceptionHandler(OutOfStockException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundException ex) {
        log.warn("NotFoundException: {}", ex.getMessage());

        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .errors(ExceptionConstants.PRODUCT_OUT_OF_STOCK.getMessage())
                .message(ex.getMessage())
                .build();
    }

    // 400 – validation error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleValidationException(MethodArgumentNotValidException ex) {
        log.warn("Validation failed: {}", ex.getMessage());

        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .errors(ExceptionConstants.VALIDATION_FAILED.getMessage())
                .details(errors)
                .build();
    }

    // General Exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);

        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errors(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ExceptionConstants.UNEXPECTED_ERROR.getMessage())
                .build();
    }

    @ExceptionHandler(FeignClientException.class)
    public ExceptionResponse handleGenericException(FeignClientException ex) {
        log.error("FeignClientException: {}, Status {}", ex.getMessage(),ex.getStatus());

        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(ex.getStatus())
                .message(ex.getMessage())
                .build();
    }
}