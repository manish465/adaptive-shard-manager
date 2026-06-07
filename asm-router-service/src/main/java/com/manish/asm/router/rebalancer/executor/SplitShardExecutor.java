package com.manish.asm.router.rebalancer.executor;

import com.manish.asm.router.metadata.AssignmentService;
import com.manish.asm.router.model.RebalanceAction;
import com.manish.asm.router.model.RebalancePlan;
import com.manish.asm.router.model.ShardAssignment;
import com.manish.asm.router.service.TopologyService;
import com.manish.asm.router.topology.TopologyChange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class SplitShardExecutor implements PlanExecutor {
    private final TopologyService topologyService;
    private final AssignmentService assignmentService;

    @Override
    public boolean supports(RebalancePlan plan) {
        return plan.action() == RebalanceAction.SPLIT_SHARD;
    }

    @Override
    public TopologyChange execute(RebalancePlan plan) {
        log.info("Splitting shard: {}", plan.shardName());
        TopologyChange change = topologyService.splitShard(plan.shardName());
        List<ShardAssignment> shardAssignments = assignmentService.applySplit(change);
        log.info("Created child shards {} and {}", shardAssignments.get(0).shardName(), shardAssignments.get(1).shardName());
        return new TopologyChange(plan.shardName(), plan.shardName() + "-1", plan.shardName() + "-2");
    }
}
