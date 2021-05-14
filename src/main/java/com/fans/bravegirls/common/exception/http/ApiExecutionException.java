package com.fans.bravegirls.common.exception.http;


public class ApiExecutionException extends RuntimeException{

    private static final long serialVersionUID = 8325124565527419666L;

    public ApiExecutionException(String message, Throwable cause) {
        super(message, cause);
    }


    public ApiExecutionException(String message) {
        super(message);
    }

}
