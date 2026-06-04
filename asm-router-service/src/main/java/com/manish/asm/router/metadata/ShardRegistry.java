package com.manish.asm.router.metadata;

import com.manish.asm.router.hashing.ConsistentHashRing;
import com.manish.asm.router.model.Shard;
import com.manish.asm.router.repository.ShardRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShardRegistry {
    private final ShardRepository shardRepository;
    private final ConsistentHashRing ring;

    @PostConstruct
    public void initialize() {
        log.info("ShardRegistry.initialize called the function");
        refresh();
    }

    public synchronized void refresh() {
        List<Shard> shards = shardRepository.findAll();
        log.info(
                "ShardRegistry.refresh with all the shards, {}",
                shards.stream().map(Shard::getShardName).collect(Collectors.toSet())
        );
        ring.buildRing(shards);
    }

    public Shard locate(String key) {
        return ring.locate(key);
    }

    public int ringSize() {
        return ring.ringSize();
    }
}
