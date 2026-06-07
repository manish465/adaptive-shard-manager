package com.manish.asm.router.rebalancer.executor;

import com.manish.asm.router.model.RebalanceAction;
import com.manish.asm.router.model.RebalancePlan;
import com.manish.asm.router.service.TopologyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SplitShardExecutor implements PlanExecutor {
    private final TopologyService topologyService;

    @Override
    public boolean supports(RebalancePlan plan) {
        return plan.action() == RebalanceAction.SPLIT_SHARD;
    }

    @Override
    public void execute(RebalancePlan plan) {
        log.info("Splitting shard: " + plan.shardName());
        topologyService.splitShard(plan.shardName());
    }
}
