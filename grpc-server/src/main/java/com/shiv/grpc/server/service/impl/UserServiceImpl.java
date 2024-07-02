package com.shiv.grpc.server.service.impl;

import com.shiv.grpc.server.beans.BeanDataModel;
import com.shiv.grpc.server.dto.UserRequestDto;
import com.shiv.grpc.server.entity.User;
import com.shiv.grpc.server.global.GenericException;
import com.shiv.grpc.server.mapper.ModelMapperUtil;
import com.shiv.grpc.server.repository.UserRepository;
import com.shiv.grpc.server.service.UserService;
import com.shiv.grpc.server.util.CryptoUtil;
import com.shiv.grpc.user.UserGrpcRequest;
import com.shiv.grpc.user.UserGrpcResponse;
import com.shiv.grpc.user.UserServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.http.HttpStatus;

@GrpcService
@Slf4j
@AllArgsConstructor
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase implements UserService {
    private final UserRepository userRepository;
    private final BeanDataModel beanDataModel;

    @Override
    public void validateUser(UserGrpcRequest request, StreamObserver<UserGrpcResponse> responseObserver) {
//        responseObserver.onNext(UserGrpcResponse.newBuilder().setName("Shiv").build());
        responseObserver.onError((Status.NOT_FOUND.withDescription("User not found"))
                .asRuntimeException());
        responseObserver.onCompleted();
    }

    @Override
    public User addUser(UserRequestDto userRequestDto) {
        final var dbExistUser = userRepository.findByEmailOrUsername(userRequestDto.getEmail(), userRequestDto.getUsername());
        if (dbExistUser.isPresent()) {
            throw new GenericException(HttpStatus.IM_USED.value(), "Duplicate user found");
        }
        final User user = ModelMapperUtil.map(userRequestDto, User.class);
        user.setPassword(CryptoUtil.encrypt(beanDataModel.getEncryptor(), user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByEmailOrUsername(username)
                .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND.value(), "User not found"));
    }
}