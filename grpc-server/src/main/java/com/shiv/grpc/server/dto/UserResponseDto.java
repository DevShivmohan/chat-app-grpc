package com.shiv.grpc.server.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class UserResponseDto implements Serializable {
    private String uuid;
    private String name;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
