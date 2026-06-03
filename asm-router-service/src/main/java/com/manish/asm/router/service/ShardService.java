package com.manish.asm.router.service;

import com.manish.asm.router.model.Shard;
import com.manish.asm.router.repository.ShardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShardService {
    private final ShardRepository shardRepository;

    public Shard createShard(String shardName, String databaseUrl) {
        Shard shard = new Shard(
                UUID.randomUUID(),
                shardName,
                "ACTIVE",
                databaseUrl,
                LocalDateTime.now()
        );

        return shardRepository.save(shard);
    }

    public List<Shard> getAllShards() {
        return shardRepository.findAll();
    }
}
