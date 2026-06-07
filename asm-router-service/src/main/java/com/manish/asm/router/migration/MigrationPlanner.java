package com.manish.asm.router.migration;

import com.manish.asm.router.metadata.AssignmentChangeSet;
import com.manish.asm.router.model.ShardAssignment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MigrationPlanner {
    public MigrationPlannerResult planSplit(AssignmentChangeSet changeSet) {
        ShardAssignment source = changeSet.original();
        List<MigrationPlan> plans = changeSet.replacements()
                        .stream()
                        .map(target -> createPlan(source, target))
                        .toList();

        return new MigrationPlannerResult(plans);
    }

    private MigrationPlan createPlan(ShardAssignment source, ShardAssignment target) {
        return new MigrationPlan(
            UUID.randomUUID(),
            source.shardName(),
            target.shardName(),
            target.startToken(),
            target.endToken()
        );
    }
}
