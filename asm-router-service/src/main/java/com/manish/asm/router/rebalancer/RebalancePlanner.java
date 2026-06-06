package com.manish.asm.router.rebalancer;

import com.manish.asm.router.dto.health.HotShardResponse;
import com.manish.asm.router.dto.rebalancer.RebalancePlanResponse;
import com.manish.asm.router.health.ShardHealthService;
import com.manish.asm.router.model.RebalanceAction;
import com.manish.asm.router.model.RebalanceReason;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RebalancePlanner {
    private final ShardHealthService healthService;

    public List<RebalancePlanResponse> generatePlan() {

        return healthService.findHotShards()
                .stream()
                .map(this::plan)
                .toList();
    }

    private RebalancePlanResponse plan(HotShardResponse shard) {
        return new RebalancePlanResponse(
                shard.shardName(),
                RebalanceReason.HOT_SHARD,
                RebalanceAction.SPLIT_SHARD,
                95
        );
    }
}
