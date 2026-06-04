package com.manish.asm.router.metadata;

import com.manish.asm.router.hashing.ConsistentHashRing;
import com.manish.asm.router.model.Shard;
import com.manish.asm.router.repository.ShardRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ShardRegistry {
    private final ShardRepository shardRepository;
    private final ConsistentHashRing ring;

    @PostConstruct
    public void initialize() {
        refresh();
    }

    public synchronized void refresh() {
        List<Shard> shards = shardRepository.findAll();
        ring.buildRing(shards);
    }

    public Shard locate(String key) {
        return ring.locate(key);
    }

    public int ringSize() {
        return ring.ringSize();
    }
}
