package com.manish.asm.router.migration;

import com.manish.asm.router.model.ShardAssignment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MigrationPlanner {
    public List<MigrationPlan> planSplit(
            ShardAssignment sourceAssignment,
            String childShard1,
            String childShard2
    ) {
        long midpoint = (sourceAssignment.startToken() + sourceAssignment.endToken()) / 2;
        List<MigrationPlan> plans = new ArrayList<>();

        plans.add(
                new MigrationPlan(
                        UUID.randomUUID(),
                        sourceAssignment.shardName(),
                        childShard1,
                        sourceAssignment.startToken(),
                        midpoint
                )
        );

        plans.add(
                new MigrationPlan(
                        UUID.randomUUID(),
                        sourceAssignment.shardName(),
                        childShard2,
                        midpoint + 1,
                        sourceAssignment.endToken()
                )
        );

        return plans;
    }
}
