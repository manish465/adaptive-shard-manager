package com.manish.asm.router.metrics;

import com.manish.asm.router.model.ShardMetrics;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MetricsRegistry {
    private final Map<String, ShardMetrics> metrics = new ConcurrentHashMap<>();

    public void put(String shardName, ShardMetrics shardMetrics) {
        metrics.put(shardName, shardMetrics);
    }

    public ShardMetrics get(String shardName) {
        return metrics.get(shardName);
    }

    public Map<String, ShardMetrics> getAll() {
        return Map.copyOf(metrics);
    }
}
