package com.manish.asm.router.health;

import com.manish.asm.router.dto.health.HotShardResponse;
import com.manish.asm.router.metrics.MetricsSimulator;
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
    private final MetricsSimulator simulator;
    private final ShardHealthEvaluator evaluator;

    public List<HotShardResponse> findHotShards() {
        return shardRepository.findAll()
                .stream()
                .map(this::evaluate)
                .filter(Objects::nonNull)
                .toList();
    }

    private HotShardResponse evaluate(Shard shard) {
        ShardMetrics metrics = simulator.generate();
        ShardHealth health = evaluator.evaluate(metrics);

        if(health != ShardHealth.HOT) return null;

        return new HotShardResponse(
                shard.getShardName(),
                metrics.cpuUsage(),
                metrics.memoryUsage(),
                metrics.storageUsage(),
                "SPLIT_RECOMMENDED"
        );
    }
}
