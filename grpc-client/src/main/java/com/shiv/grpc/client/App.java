package com.shiv.grpc.client;

import com.shiv.grpc.client.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@AllArgsConstructor
@SpringBootApplication
@Slf4j
public class App {
    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @PostConstruct
    public void callRPCService() throws InterruptedException {
        log.info("gRPC response {}", userService.getUser());
        log.info("gRPC response {}", userService.getUsers());
    }
}
