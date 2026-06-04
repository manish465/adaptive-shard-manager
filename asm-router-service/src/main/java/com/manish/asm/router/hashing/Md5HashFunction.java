package com.manish.asm.router.hashing;

import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Md5HashFunction implements HashFunction {
    @Override
    public long hash(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(value.getBytes(StandardCharsets.UTF_8));

            long result = 0;
            for (int i = 0; i < digest.length; i++) {
                result ^= ((long) digest[i] & 0xff) << ((i % 8) * 8);
            }

            return result & Long.MAX_VALUE;
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
