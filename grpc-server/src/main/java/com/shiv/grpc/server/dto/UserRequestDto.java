package com.shiv.grpc.server.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserRequestDto implements Serializable {
    @NotNull
    @NotBlank
    @Size(min = 3,max = 30)
    private String name;
    @NotNull
    @NotBlank
    @Size(min = 5,max = 30)
    @Pattern(regexp = "[a-zA-Z0-9]+",message = "Invalid username")
    private String username;
    @Email
    @NotBlank
    @NotNull
    @Size(min = 12,max = 60)
    private String email;
    @NotBlank
    @NotNull
    @Size(min = 8,max = 60)
    private String password;
}
