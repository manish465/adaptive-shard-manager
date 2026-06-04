package com.manish.asm.router.service;

import com.manish.asm.router.hashing.ConsistentHashRing;
import com.manish.asm.router.model.Shard;
import com.manish.asm.router.repository.ShardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoutingService {
    private final ShardRepository shardRepository;
    private final ConsistentHashRing ring;

    public Shard route(String key) {
        ring.buildRing(shardRepository.findAll());
        return ring.locate(key);
    }
}
