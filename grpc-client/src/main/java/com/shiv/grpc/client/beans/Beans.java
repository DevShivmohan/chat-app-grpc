package com.shiv.grpc.client.beans;

import com.shiv.grpc.client.interceptor.ClientInterceptorImpl;
import net.devh.boot.grpc.client.interceptor.GlobalClientInterceptorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    @Bean
    public GlobalClientInterceptorConfigurer globalInterceptorConfigurerAdapter() {
        return registry -> registry.add(new ClientInterceptorImpl());
    }
}
