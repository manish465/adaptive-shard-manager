package com.manish.asm.router.metadata;

import com.manish.asm.router.dto.metadata.RingSummaryResponse;
import com.manish.asm.router.hashing.ConsistentHashRing;
import com.manish.asm.router.hashing.RingFactory;
import com.manish.asm.router.model.Shard;
import com.manish.asm.router.repository.ShardRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ShardRegistry {
    private final ShardRepository shardRepository;
    private final ConsistentHashRing ring;

    public ShardRegistry(
            ShardRepository shardRepository,
            RingFactory ringFactory
    ) {
        this.shardRepository = shardRepository;
        ring = ringFactory.createRing();
    }

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

    public RingSummaryResponse getSummary() {
        return new RingSummaryResponse(
                shardRepository.count() > Integer.MAX_VALUE
                        ? Integer.MAX_VALUE
                        : (int) shardRepository.count(),
                ringSize()
        );
    }
}
