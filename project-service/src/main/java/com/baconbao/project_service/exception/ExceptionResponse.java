package com.baconbao.project_service.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionResponse {
    private LocalDateTime time;
    private String message;
    private String details;
    private boolean success;

    public ExceptionResponse(LocalDateTime time, String message,String details, boolean success) {
        this.time = time;
        this.message = message;
        this.details = details;
        this.success = success;
    }
}
