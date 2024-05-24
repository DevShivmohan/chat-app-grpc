package com.shiv.grpc.client.service.impl;

import com.shiv.grpc.client.service.UserService;
import com.shiv.grpc.user.User;
import com.shiv.grpc.user.UserServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    /**
     * It is used for synchronousClient
     */
    @GrpcClient("com-shiv-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @Override
    public User getUser() {
        return userServiceBlockingStub.getUser(User.newBuilder().setId(5).build());
    }
}
