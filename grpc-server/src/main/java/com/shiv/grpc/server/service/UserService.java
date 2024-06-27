package com.shiv.grpc.server.service;

import com.shiv.grpc.user.User;
import io.grpc.stub.StreamObserver;

public interface UserService {
    void getUser(User request, StreamObserver<User> responseObserver);
    StreamObserver<User> getUsers(StreamObserver<User> responseObserver);
}
