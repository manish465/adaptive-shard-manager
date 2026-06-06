package com.manish.asm.router.dto.health;

public record HotShardResponse(
        String shardName,
        double cpuUsage,
        double memoryUsage,
        double storageUsage,
        String recommendation
) {
}
