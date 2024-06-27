package com.shiv.grpc.server.util;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CryptoUtil {
    public static String encrypt(Cipher cipher,String data){
        try{
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        }catch (Exception e){
            return null;
        }
    }

    public static String decrypt(Cipher cipher,String data){
        try{
            return new String(cipher.doFinal(Base64.getDecoder().decode(data)));
        }catch (Exception e){
            return null;
        }
    }
}
