package com.manish.asm.router.rebalancer;

import com.manish.asm.router.dto.health.HotShardResponse;
import com.manish.asm.router.dto.rebalancer.RebalancePlanResponse;
import com.manish.asm.router.health.ShardHealthService;
import com.manish.asm.router.model.RebalanceAction;
import com.manish.asm.router.model.RebalanceReason;
import com.manish.asm.router.model.ShardMetrics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RebalancePlanner {
    private final ShardHealthService healthService;
    private final PriorityCalculator priorityCalculator;

    public List<RebalancePlanResponse> generatePlan() {

        return healthService.findHotShards()
                .stream()
                .map(this::toShardMetrics)
                .map(this::plan)
                .sorted(Comparator.comparingInt(RebalancePlanResponse::priority).reversed())
                .toList();
    }

    private RebalancePlanResponse plan(ShardMetrics shard) {
        return new RebalancePlanResponse(
                shard.shardName(),
                determineReason(shard),
                RebalanceAction.SPLIT_SHARD,
                priorityCalculator.calculate(shard)
        );
    }

    private ShardMetrics toShardMetrics(HotShardResponse shard) {
        return new ShardMetrics(
                shard.shardName(),
                shard.cpuUsage(),
                shard.memoryUsage(),
                shard.storageUsage(),
                shard.requestsPerMinute()
        );
    }

    private RebalanceReason determineReason(ShardMetrics metrics) {

        if (metrics.cpuUsage() >= 80) {
            return RebalanceReason.CPU_PRESSURE;
        }

        if (metrics.memoryUsage() >= 80) {
            return RebalanceReason.MEMORY_PRESSURE;
        }

        if (metrics.storageUsage() >= 90) {
            return RebalanceReason.STORAGE_PRESSURE;
        }

        return RebalanceReason.TRAFFIC_PRESSURE;
    }
}
