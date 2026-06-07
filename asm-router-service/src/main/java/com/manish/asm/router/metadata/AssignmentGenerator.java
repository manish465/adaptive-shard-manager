package com.manish.asm.router.metadata;

import com.manish.asm.router.model.Shard;
import com.manish.asm.router.model.ShardAssignment;
import com.manish.asm.router.model.ShardStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AssignmentGenerator {
    private static final long MAX_TOKEN = 1_000_000L;

    public List<ShardAssignment> generate(List<Shard> shards) {
        List<ShardAssignment> assignments = new ArrayList<>();
        if (shards.isEmpty()) return assignments;

        shards = shards
                .stream()
                .filter(shard -> shard.getStatus() == ShardStatus.ACTIVE)
                .toList();

        long rangeSize = MAX_TOKEN / shards.size();

        for (int i = 0; i < shards.size(); i++) {
            long start = i * rangeSize;
            long end = (i == shards.size() - 1) ? MAX_TOKEN : ((i + 1) * rangeSize) - 1;

            assignments.add(new ShardAssignment(
                shards.get(i).getId(),
                shards.get(i).getShardName(),
                start,
                end
            ));
        }

        return assignments;
    }
}
