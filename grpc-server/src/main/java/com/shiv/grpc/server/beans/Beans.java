package com.shiv.grpc.server.beans;

import com.shiv.grpc.server.interceptor.ServerInterceptorImpl;
import io.grpc.ServerInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Configuration
@Component
public class Beans {
    @Value("${chat.service.crypto-secret}")
    private String secret;

    @Bean
    public BeanDataModel beanDataModel() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        byte[] key = secret.getBytes(StandardCharsets.UTF_8);
        final var sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        final var secretKey = new SecretKeySpec(key, "AES");
        final var encryptor = Cipher.getInstance("AES/ECB/PKCS5Padding");
        encryptor.init(Cipher.ENCRYPT_MODE, secretKey);
        final var decryptor = Cipher.getInstance("AES/ECB/PKCS5Padding");
        decryptor.init(Cipher.DECRYPT_MODE, secretKey);
        return BeanDataModel.builder()
                .encryptor(encryptor)
                .decryptor(decryptor)
                .build();
    }

    @Bean
    @GrpcGlobalServerInterceptor
    public ServerInterceptor jwtServerInterceptor() {
        return new ServerInterceptorImpl();
    }
}
