package com.manish.asm.router.metadata;

import com.manish.asm.router.model.ShardAssignment;
import com.manish.asm.router.topology.TopologyChange;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AssignmentSplitPlanner {
    public List<ShardAssignment> split(ShardAssignment original, TopologyChange change) {
        long midpoint = (original.startToken() + original.endToken()) / 2;

        ShardAssignment child1 = new ShardAssignment(
            UUID.randomUUID(),
            change.childShard1(),
            original.startToken(),
            midpoint
        );

        ShardAssignment child2 = new ShardAssignment(
            UUID.randomUUID(),
            change.childShard2(),
            midpoint + 1,
            original.endToken()
        );

        return List.of(child1, child2);
    }
}
