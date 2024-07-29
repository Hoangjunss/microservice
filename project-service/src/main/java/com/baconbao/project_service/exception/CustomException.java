package com.baconbao.project_service.exception;

import org.springframework.http.HttpStatusCode;

public class CustomException extends RuntimeException{
    private  final Error error;

    public CustomException(Error error){
        super(error.getMessage());
        this.error = error;
    }

    public Error getError() {
        return error;
    }
    public int getCode(){
        return error.getCode();
    }
    public String getMessage() {
        return error.getMessage();
    }
    public HttpStatusCode getHttpStatusCode(){
        return error.getStatusCode();
    }

}
