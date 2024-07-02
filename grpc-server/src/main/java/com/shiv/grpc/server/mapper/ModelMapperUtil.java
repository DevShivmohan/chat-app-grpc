package com.shiv.grpc.server.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelMapperUtil {
    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    /**
     * Generic mapper
     *
     * @param sourceObject
     * @param targetClass
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T map(S sourceObject, Class<T> targetClass) {
        return sourceObject == null ? null : modelMapper.map(sourceObject, targetClass);
    }
}
