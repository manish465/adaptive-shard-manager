package com.manish.asm.router.metadata;

import com.manish.asm.router.model.ShardAssignment;
import com.manish.asm.router.topology.SplitOperation;
import com.manish.asm.router.topology.TopologyChange;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AssignmentSplitPlanner {
    public List<ShardAssignment> split(ShardAssignment original, SplitOperation change) {
        long midpoint = (original.startToken() + original.endToken()) / 2;

        ShardAssignment child1 = new ShardAssignment(
            UUID.randomUUID(),
            change.targetShards().get(0),
            original.startToken(),
            midpoint
        );

        ShardAssignment child2 = new ShardAssignment(
            UUID.randomUUID(),
            change.targetShards().get(1),
            midpoint + 1,
            original.endToken()
        );

        return List.of(child1, child2);
    }
}
