package com.baconbao.image_service.exception;

public class CloudinaryException extends RuntimeException{
    private final Error error;

    public CloudinaryException(Error error) {
        super(error.getMessage());
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
