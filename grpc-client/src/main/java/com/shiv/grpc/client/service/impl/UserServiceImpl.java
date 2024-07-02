package com.shiv.grpc.client.service.impl;

import com.shiv.grpc.client.service.UserService;
import com.shiv.grpc.user.UserGrpcRequest;
import com.shiv.grpc.user.UserGrpcResponse;
import com.shiv.grpc.user.UserServiceGrpc;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    /**
     * It is used for synchronous client
     */
    @GrpcClient("com-shiv-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    /**
     * It is used for asynchronous client
     */
    @GrpcClient("com-shiv-service")
    private UserServiceGrpc.UserServiceStub userServiceStub;

    @PostConstruct
    @Override
    public UserGrpcResponse getUser() {
        try{
            final var user=userServiceBlockingStub.getUser(UserGrpcRequest.newBuilder().setToken("Bearer tytw").build());
            log.info("User info response {}",user);
            return user;
        }catch (StatusRuntimeException statusRuntimeException){
            log.error("gRPC calls error status code {} and error message {}",statusRuntimeException.getStatus().getCode(),statusRuntimeException.getStatus().getDescription());
            return null;
        }
    }
}
