package com.manish.asm.router.hashing;

import org.springframework.stereotype.Component;

@Component
public class SimpleHashFunction implements HashFunction {
    @Override
    public long hash(String value) {
        return Integer.toUnsignedLong(value.hashCode());
    }
}
