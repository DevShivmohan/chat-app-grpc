package com.shiv.grpc.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ErrorResponseDto implements Serializable {
    private int statusCode;
    private String errorMessage;
}
