package com.manish.asm.router.metadata;

import com.manish.asm.router.repository.entity.Shard;
import com.manish.asm.router.model.ShardAssignment;
import com.manish.asm.router.repository.ShardRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AssignmentInitializer {
    private static final long MAX_TOKEN = 1_000_000L;

    private final ShardRepository shardRepository;
    private final ShardAssignmentRegistry registry;

    @PostConstruct
    public void initialize() {
        List<Shard> shards = shardRepository.findAll();
        if (shards.isEmpty()) return;

        long rangeSize = MAX_TOKEN / shards.size();

        for (int i = 0; i < shards.size(); i++) {
            long start = i * rangeSize;
            long end = (i == shards.size() - 1) ? MAX_TOKEN : ((i + 1) * rangeSize) - 1;

            registry.save(new ShardAssignment(
                UUID.randomUUID(),
                shards.get(i).getShardName(),
                start,
                end
            ));
        }
    }
}
