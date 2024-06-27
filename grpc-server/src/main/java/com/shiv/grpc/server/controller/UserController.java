package com.shiv.grpc.server.controller;

import com.shiv.grpc.server.dto.UserRequestDto;
import com.shiv.grpc.server.dto.UserResponseDto;
import com.shiv.grpc.server.mapper.ModelMapperUtil;
import com.shiv.grpc.server.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Log4j2
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody @Valid UserRequestDto userRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ModelMapperUtil.map(userService.addUser(userRequestDto), UserResponseDto.class));
    }
}
