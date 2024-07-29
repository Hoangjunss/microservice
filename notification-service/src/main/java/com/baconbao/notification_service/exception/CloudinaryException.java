package com.baconbao.notification_service.exception;

public class CloudinaryException extends RuntimeException{
    private final java.lang.Error error;

    public CloudinaryException(java.lang.Error error) {
        super(error.getMessage());
        this.error = error;
    }

    public java.lang.Error getError() {
        return error;
    }
}
