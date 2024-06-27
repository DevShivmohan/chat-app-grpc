package com.shiv.grpc.server.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.crypto.Cipher;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BeanDataModel {
    private Cipher encryptor;
    private Cipher decryptor;
}
