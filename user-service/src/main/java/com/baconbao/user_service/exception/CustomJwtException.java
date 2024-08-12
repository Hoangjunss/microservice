package com.baconbao.user_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class CustomJwtException extends RuntimeException {

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    public CustomJwtException(Error error, Throwable cause) {
        super(error.getMessage(), cause);
        this.code = error.getCode();
        this.message = error.getMessage();
        this.statusCode = error.getStatusCode();
    }
}