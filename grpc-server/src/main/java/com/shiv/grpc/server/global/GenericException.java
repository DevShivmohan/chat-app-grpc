package com.shiv.grpc.server.global;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GenericException extends RuntimeException{
    private final int statusCode;
    private final String errorMessage;

    public GenericException(int statusCode, String errorMessage) {
        super(errorMessage);
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }
}
