package com.manish.asm.router.rebalancer;

import com.manish.asm.router.dto.health.HotShardResponse;
import com.manish.asm.router.dto.rebalancer.RebalancePlanResponse;
import com.manish.asm.router.health.ShardHealthService;
import com.manish.asm.router.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RebalancePlanner {
    private final ShardHealthService healthService;
    private final PriorityCalculator priorityCalculator;
    private final RebalancePlanRegistry registry;

    public List<RebalancePlanResponse> generatePlan() {

        return healthService.findHotShards()
                .stream()
                .map(this::toShardMetrics)
                .map(this::plan)
                .sorted(Comparator.comparingInt(RebalancePlanResponse::priority).reversed())
                .toList();
    }

    public void createTestPlan() {
        RebalancePlan plan = new RebalancePlan(
                UUID.randomUUID(),
                "shard-a",
                RebalanceReason.CPU_PRESSURE,
                RebalanceAction.SPLIT_SHARD,
                95,
                PlanStatus.CREATED,
                LocalDateTime.now()
        );

        registry.save(plan);
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
