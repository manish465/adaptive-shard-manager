package com.manish.asm.router.service;

import com.manish.asm.router.dto.ShardResponse;
import com.manish.asm.router.model.Shard;
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

    public ShardResponse createShard(String shardName, String databaseUrl) {
        Shard shard = new Shard(
                UUID.randomUUID(),
                shardName,
                "ACTIVE",
                databaseUrl,
                LocalDateTime.now()
        );

        Shard saved = shardRepository.save(shard);
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
