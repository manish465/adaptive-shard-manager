package com.manish.asm.router.service;

import com.manish.asm.router.metadata.ShardRegistry;
import com.manish.asm.router.model.Shard;
import com.manish.asm.router.model.ShardStatus;
import com.manish.asm.router.repository.ShardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TopologyService {
    private final ShardRepository shardRepository;
    private final ShardRegistry shardRegistry;

    public void splitShard(String shardName) {
        Shard original = shardRepository
                .findByShardName(shardName)
                .orElseThrow();

        Shard splittingShard = new Shard(
                original.getId(),
                original.getShardName(),
                ShardStatus.SPLITTING,
                original.getDatabaseUrl(),
                original.getCreatedAt()
        );

        shardRepository.save(splittingShard);
        createChildShard(shardName + "-1", original.getDatabaseUrl());
        createChildShard(shardName + "-2", original.getDatabaseUrl());
        shardRegistry.refresh();
    }

    private void createChildShard(String shardName, String databaseUrl) {
        Shard child = new Shard(
                UUID.randomUUID(),
                shardName,
                ShardStatus.ACTIVE,
                databaseUrl,
                LocalDateTime.now()
        );
        shardRepository.save(child);
    }
}
