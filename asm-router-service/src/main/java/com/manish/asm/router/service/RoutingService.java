package com.manish.asm.router.service;

import com.manish.asm.router.metadata.ShardRegistry;
import com.manish.asm.router.model.Shard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoutingService {
    private final ShardRegistry shardRegistry;

    public Shard route(String key) {
        return shardRegistry.locate(key);
    }
}
