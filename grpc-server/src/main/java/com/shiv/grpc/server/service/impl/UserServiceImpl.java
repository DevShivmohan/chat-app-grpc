package com.shiv.grpc.server.service.impl;

import com.shiv.grpc.server.service.UserService;
import com.shiv.grpc.user.User;
import com.shiv.grpc.user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

@GrpcService
@Slf4j
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase implements UserService {
    @Override
    public void getUser(User request, StreamObserver<User> responseObserver) {
        log.info("gRPC request {}",request);
        responseObserver.onNext(User.newBuilder().setId(1).setName("Shiv").setIsActive(true).build());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<User> getUsers(StreamObserver<User> responseObserver) {
        return new StreamObserver<>() {
            private final List<User> usersRequest = new ArrayList<>();

            @Override
            public void onNext(User user) {
                usersRequest.add(user);
            }

            @Override
            public void onError(Throwable throwable) {
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                log.info("All usersRequest {}", usersRequest);
                usersRequest.forEach(responseObserver::onNext);
                responseObserver.onCompleted();
            }
        };
    }
}