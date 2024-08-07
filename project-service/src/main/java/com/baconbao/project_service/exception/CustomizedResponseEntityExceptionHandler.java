package com.baconbao.project_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.baconbao.project_service.dto.ApiResponse;


@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ApiResponse<String>> handleBadRequestException(BadRequestException ex, WebRequest request) {
        ApiResponse<String> response = new ApiResponse<>(
            false,
            ex.getMessage(),
            ""
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<ApiResponse<String>> handleCustomException(CustomException cx, WebRequest request) {
        ApiResponse<String> response = new ApiResponse<>(
            false,
            cx.getMessage(),
            ""
        );
        return new ResponseEntity<>(response, cx.getError().getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiResponse<String>> handleAllException(Exception e, WebRequest request) {
        ApiResponse<String> response = new ApiResponse<>(
            false,
            e.getMessage(),
            ""
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
