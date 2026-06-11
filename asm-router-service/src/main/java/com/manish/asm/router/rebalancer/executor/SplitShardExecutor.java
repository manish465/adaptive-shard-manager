package com.manish.asm.router.rebalancer.executor;

import com.manish.asm.router.metadata.AssignmentChangeSet;
import com.manish.asm.router.metadata.AssignmentService;
import com.manish.asm.router.migration.MigrationPlanner;
import com.manish.asm.router.migration.MigrationPlannerResult;
import com.manish.asm.router.migration.MigrationService;
import com.manish.asm.router.migration.MigrationTaskGenerator;
import com.manish.asm.router.model.RebalanceAction;
import com.manish.asm.router.model.RebalancePlan;
import com.manish.asm.router.topology.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class SplitShardExecutor implements PlanExecutor {
    private final TopologyService topologyService;
    private final AssignmentService assignmentService;
    private final MigrationPlanner migrationPlanner;
    private final MigrationService migrationService;
    private final SplitOperationRegistry splitOperationRegistry;

    @Override
    public boolean supports(RebalancePlan plan) {
        return plan.action() == RebalanceAction.SPLIT_SHARD;
    }

    @Override
    public SplitOperation execute(RebalancePlan plan) {
        log.info("Splitting shard: {}", plan.shardName());

        SplitOperation change = topologyService.splitShard(plan.shardName());
        SplitOperation operation = new SplitOperation(
                change.operationId(),
                OperationType.SPLIT,
                change.sourceShard(),
                List.of(
                        change.targetShards().get(0),
                        change.targetShards().get(1)
                ),
                SplitOperationStatus.CREATED,
                LocalDateTime.now()
        );

        splitOperationRegistry.save(operation);
        splitOperationRegistry.updateStatus(
                change.operationId(),
                SplitOperationStatus.IN_PROGRESS
        );

        AssignmentChangeSet assignmentChange = assignmentService.applySplit(change);
        MigrationPlannerResult result = migrationPlanner.planSplit(assignmentChange, change);

        log.info("Created child shards {} and {}", assignmentChange.replacements().get(0).shardName(), assignmentChange.replacements().get(1).shardName());
        migrationService.createTasks(result);

        return change;
    }
}
