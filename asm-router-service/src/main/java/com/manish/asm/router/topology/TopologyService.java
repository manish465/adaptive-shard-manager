package com.manish.asm.router.topology;

import com.manish.asm.router.metadata.ShardRegistry;
import com.manish.asm.router.repository.entity.Shard;
import com.manish.asm.router.model.ShardStatus;
import com.manish.asm.router.repository.ShardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TopologyService {
    private final ShardRepository shardRepository;
    private final ShardRegistry shardRegistry;

    public SplitOperation splitShard(String shardName) {
        Shard original = shardRepository.findByShardName(shardName).orElseThrow();
        String childShard1 = shardName + "-1";
        String childShard2 = shardName + "-2";

        original.setStatus(ShardStatus.SPLITTING);
        shardRepository.save(original);
        createChildShard(childShard1, original.getDatabaseUrl());
        createChildShard(childShard2, original.getDatabaseUrl());
        shardRegistry.refresh();

        return new SplitOperation(
                UUID.randomUUID(),
                OperationType.SPLIT,
                shardName,
                List.of(childShard1, childShard2),
                SplitOperationStatus.IN_PROGRESS,
                LocalDateTime.now()
        );
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
