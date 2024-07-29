package com.baconbao.profile_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException ex, WebRequest request){
        return new ResponseEntity<>(new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false), false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<ExceptionResponse> handleCustomException(CustomException cx, WebRequest request){
        return new ResponseEntity<>(new ExceptionResponse(LocalDateTime.now(), cx.getMessage(), request.getDescription(false), false), cx.getError().getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllException(Exception e, WebRequest request){
        return new ResponseEntity<>(new ExceptionResponse(LocalDateTime.now(), e.getMessage(), request.getDescription(false), false), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
