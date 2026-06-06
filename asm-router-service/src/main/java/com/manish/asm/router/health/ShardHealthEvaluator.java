package com.manish.asm.router.health;

import com.manish.asm.router.model.ShardHealth;
import com.manish.asm.router.model.ShardMetrics;
import org.springframework.stereotype.Component;

@Component
public class ShardHealthEvaluator {
    public ShardHealth evaluate(ShardMetrics metrics) {
        if(
            metrics.cpuUsage() > 80
            || metrics.memoryUsage() > 80
            || metrics.storageUsage() > 90
        ) return ShardHealth.HOT;

        if(
            metrics.cpuUsage() > 60
            || metrics.memoryUsage() > 60
            || metrics.storageUsage() > 75
        ) return ShardHealth.WARNING;

        return ShardHealth.HEALTHY;
    }
}
