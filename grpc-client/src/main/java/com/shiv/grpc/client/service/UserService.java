package com.shiv.grpc.client.service;

import com.shiv.grpc.user.UserGrpcResponse;

public interface UserService {
    UserGrpcResponse getUser();
}
