package com.shiv.grpc.client.service;

import com.shiv.grpc.user.User;

import java.util.List;

public interface UserService {
    User getUser();
    List<User> getUsers() throws InterruptedException;
}
