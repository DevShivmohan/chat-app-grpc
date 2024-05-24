package com.shiv.grpc.server.service;

import com.shiv.grpc.user.User;
import com.shiv.grpc.user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@Slf4j
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase{
    @Override
    public void getUser(User request, StreamObserver<User> responseObserver) {
        log.info("gRPC request {}",request);
        responseObserver.onNext(User.newBuilder().setId(1).setName("Shiv").setIsActive(true).build());
        responseObserver.onCompleted();
    }
}