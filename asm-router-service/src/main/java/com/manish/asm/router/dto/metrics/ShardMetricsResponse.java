package com.manish.asm.router.dto.metrics;

import com.manish.asm.router.model.ShardHealth;

public record ShardMetricsResponse(
        String shardName,
        double cpuUsage,
        double memoryUsage,
        double storageUsage,
        long requestsPerMinute,
        ShardHealth health
) {
}
