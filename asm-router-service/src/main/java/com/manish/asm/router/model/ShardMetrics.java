package com.manish.asm.router.model;

public record ShardMetrics(
        double cpuUsage,
        double memoryUsage,
        double storageUsage,
        long requestsPerMinute
) {
}
