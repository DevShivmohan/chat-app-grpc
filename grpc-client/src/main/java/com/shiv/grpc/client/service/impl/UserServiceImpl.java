package com.shiv.grpc.client.service.impl;

import com.shiv.grpc.client.service.UserService;
import com.shiv.grpc.user.User;
import com.shiv.grpc.user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

    @Override
    public User getUser() {
        return userServiceBlockingStub.getUser(User.newBuilder().setId(5).build());
    }

    @Override
    public List<User> getUsers() throws InterruptedException {
        final List<User> users = List.of(
                User.newBuilder().setId(1).setName("S").setIsActive(true).build(),
                User.newBuilder().setId(2).setName("S").setIsActive(true).build(),
                User.newBuilder().setId(3).setName("S").setIsActive(true).build(),
                User.newBuilder().setId(4).setName("S").setIsActive(true).build()
        );
        final List<User> response = new ArrayList<>();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final StreamObserver<User> streamObserver = userServiceStub.getUsers(new StreamObserver<>() {
            @Override
            public void onNext(User user) {
                response.add(user);
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        users.forEach(streamObserver::onNext);
        streamObserver.onCompleted();
        return countDownLatch.await(1, TimeUnit.MINUTES) ? response : Collections.emptyList();
    }
}
