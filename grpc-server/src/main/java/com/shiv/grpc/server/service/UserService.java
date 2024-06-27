package com.shiv.grpc.server.service;

import com.shiv.grpc.server.dto.UserRequestDto;
import com.shiv.grpc.server.entity.User;

public interface UserService {
//    void getUser(User request, StreamObserver<User> responseObserver);
//    StreamObserver<User> getUsers(StreamObserver<User> responseObserver);
    User addUser(UserRequestDto userRequestDto);
}
