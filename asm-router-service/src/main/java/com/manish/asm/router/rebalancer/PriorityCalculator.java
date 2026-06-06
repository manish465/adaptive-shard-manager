package com.manish.asm.router.rebalancer;

import com.manish.asm.router.model.ShardMetrics;
import org.springframework.stereotype.Component;

@Component
public class PriorityCalculator {
    public int calculate(ShardMetrics metrics) {
        double score = 0;

        score += metrics.cpuUsage() * 0.40;
        score += metrics.memoryUsage() * 0.25;
        score += metrics.storageUsage() * 0.25;
        score += Math.min(metrics.requestsPerMinute() / 100.0, 100) * 0.10;

        return (int) Math.round(score);
    }
}
