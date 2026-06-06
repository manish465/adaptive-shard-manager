package com.manish.asm.router.hashing;

import com.manish.asm.router.config.ShardingProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RingFactory {
    private final HashFunction hashFunction;
    private final ShardingProperties properties;

    public ConsistentHashRing createRing() {
        return new ConsistentHashRing(
                hashFunction,
                properties
        );
    }
}
