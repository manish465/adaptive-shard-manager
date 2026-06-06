package com.manish.asm.router.service;

import com.manish.asm.router.dto.RingSummaryResponse;
import com.manish.asm.router.dto.ShardResponse;
import com.manish.asm.router.metadata.ShardRegistry;
import com.manish.asm.router.model.Shard;
import com.manish.asm.router.model.ShardStatus;
import com.manish.asm.router.repository.ShardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShardService {
    private final ShardRepository shardRepository;
    private final ShardRegistry shardRegistry;

    public ShardResponse createShard(String shardName, String databaseUrl) {
        Shard shard = new Shard(
                UUID.randomUUID(),
                shardName,
                ShardStatus.ACTIVE,
                databaseUrl,
                LocalDateTime.now()
        );

        Shard saved = shardRepository.save(shard);
        shardRegistry.refresh();

        return map(saved);
    }

    public List<ShardResponse> getAllShards() {
        return shardRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    private ShardResponse map(Shard shard) {
        return new ShardResponse(
                shard.getId(),
                shard.getShardName(),
                shard.getStatus(),
                shard.getDatabaseUrl(),
                shard.getCreatedAt()
        );
    }
}
