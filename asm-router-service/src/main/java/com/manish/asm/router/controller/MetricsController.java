package com.manish.asm.router.controller;

import com.manish.asm.router.dto.metrics.ShardMetricsResponse;
import com.manish.asm.router.health.ShardHealthEvaluator;
import com.manish.asm.router.metrics.MetricsRegistry;
import com.manish.asm.router.model.Shard;
import com.manish.asm.router.model.ShardHealth;
import com.manish.asm.router.model.ShardMetrics;
import com.manish.asm.router.repository.ShardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/metrics")
@RequiredArgsConstructor
public class MetricsController {
    private final ShardRepository shardRepository;
    private final ShardHealthEvaluator evaluator;
    private final MetricsRegistry registry;

    @GetMapping("/shards")
    public List<ShardMetricsResponse> getMetrics() {
        return shardRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    private ShardMetricsResponse map(
            Shard shard
    ){
        ShardMetrics metrics = registry.get(shard.getShardName());
        ShardHealth health = evaluator.evaluate(metrics);

        return new ShardMetricsResponse(
                shard.getShardName(),
                metrics.cpuUsage(),
                metrics.memoryUsage(),
                metrics.storageUsage(),
                metrics.requestsPerMinute(),
                health
        );
    }
}
