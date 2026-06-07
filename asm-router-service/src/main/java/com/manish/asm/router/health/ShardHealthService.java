package com.manish.asm.router.health;

import com.manish.asm.router.dto.health.HotShardResponse;
import com.manish.asm.router.metrics.MetricsRegistry;
import com.manish.asm.router.model.Shard;
import com.manish.asm.router.model.ShardHealth;
import com.manish.asm.router.model.ShardMetrics;
import com.manish.asm.router.repository.ShardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShardHealthService {
    private final ShardRepository shardRepository;
    private final ShardHealthEvaluator evaluator;
    private final MetricsRegistry registry;

    public List<HotShardResponse> findHotShards() {
        return shardRepository.findAll()
                .stream()
                .map(this::evaluate)
                .filter(Objects::nonNull)
                .toList();
    }

    private HotShardResponse evaluate(Shard shard) {
        ShardMetrics metrics = registry.get(shard.getShardName());
        ShardHealth health = evaluator.evaluate(metrics);

        if(health != ShardHealth.HOT) return null;

        return new HotShardResponse(
                shard.getShardName(),
                metrics.cpuUsage(),
                metrics.memoryUsage(),
                metrics.storageUsage(),
                metrics.requestsPerMinute(),
                "SPLIT_RECOMMENDED"
        );
    }
}
