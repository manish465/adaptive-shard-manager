package com.manish.asm.router.rebalancer.executor;

import com.manish.asm.router.metadata.AssignmentChangeSet;
import com.manish.asm.router.metadata.AssignmentService;
import com.manish.asm.router.migration.MigrationPlanner;
import com.manish.asm.router.migration.MigrationPlannerResult;
import com.manish.asm.router.migration.MigrationService;
import com.manish.asm.router.model.RebalanceAction;
import com.manish.asm.router.model.RebalancePlan;
import com.manish.asm.router.topology.TopologyService;
import com.manish.asm.router.topology.SplitOperationRegistry;
import com.manish.asm.router.topology.TopologyChange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SplitShardExecutor implements PlanExecutor {
    private final TopologyService topologyService;
    private final AssignmentService assignmentService;
    private final MigrationPlanner migrationPlanner;
    private final MigrationService migrationService;
    private final SplitOperationRegistry operationRegistry;

    @Override
    public boolean supports(RebalancePlan plan) {
        return plan.action() == RebalanceAction.SPLIT_SHARD;
    }

    @Override
    public TopologyChange execute(RebalancePlan plan) {
        log.info("Splitting shard: {}", plan.shardName());

        TopologyChange change = topologyService.splitShard(plan.shardName());
        operationRegistry.save(change);

        AssignmentChangeSet assignmentChange = assignmentService.applySplit(change);
        MigrationPlannerResult result = migrationPlanner.planSplit(assignmentChange, change);

        log.info("Created child shards {} and {}", assignmentChange.replacements().get(0).shardName(), assignmentChange.replacements().get(1).shardName());
        migrationService.createTasks(result);

        return change;
    }
}
