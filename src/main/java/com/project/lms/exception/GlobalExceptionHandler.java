package com.project.lms.exception;

import com.project.lms.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<ApiResponse<?>> handleAppException(ErrorException ex) {

        ApiResponse<?> response = new ApiResponse<>(
                ex.getCode(),
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(SuccessException.class)
    public ResponseEntity<ApiResponse<Object>> handleSuccess(SuccessException ex) {
        ApiResponse<Object> response =
                new ApiResponse<>(ex.getCode(), ex.getMessage(), ex.getData());

        return ResponseEntity
                .status(HttpStatus.OK)     // keep status OK
                .body(response);
    }
}