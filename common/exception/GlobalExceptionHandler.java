package com.ktn3.core_banking.common.exception;

import com.ktn3.core_banking.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // =========================
    // 400 - Validation (@Valid DTO)
    // =========================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return build(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", message);
    }

    // =========================
    // 400 - JSON sai format
    // =========================
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleBadJson(HttpMessageNotReadableException ex) {

        return build(HttpStatus.BAD_REQUEST,
                "BAD_REQUEST",
                "Request body không hợp lệ");
    }

    // =========================
    // 404 - URL không tồn tại
    // =========================
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse>
    	handleNoResourceFoundException (NoResourceFoundException ex){

		return build(HttpStatus.NOT_FOUND,
                "NOT_FOUND",
                "404 Not Found. URL may not exist...");
    }

    // =========================
    // 405 - Sai HTTP method
    // =========================
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException ex) {

        return build(HttpStatus.METHOD_NOT_ALLOWED,
                "METHOD_NOT_ALLOWED",
                "HTTP method không được hỗ trợ");
    }

    // =========================
    // 500 - All remaining errors
    // =========================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex) {

        log.error("SYSTEM_ERROR: ", ex);

        return build(HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                "Lỗi hệ thống");
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex) {

    	return build(HttpStatus.BAD_REQUEST,
                "BAD_REQUEST",
                ex.getMessage());
    }
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex) {

    	return build(HttpStatus.BAD_REQUEST,
                "BAD_REQUEST",
                ex.getMessage());
    }

    // =========================
    // common response builder
    // =========================
    private ResponseEntity<ErrorResponse> build(
            HttpStatus status,
            String errorCode,
            String message) {

        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .status(status.value())
                        .errorCode(errorCode)
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}