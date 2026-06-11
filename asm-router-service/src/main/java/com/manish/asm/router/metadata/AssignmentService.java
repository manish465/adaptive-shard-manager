package com.manish.asm.router.metadata;

import com.manish.asm.router.model.ShardAssignment;
import com.manish.asm.router.topology.SplitOperation;
import com.manish.asm.router.topology.TopologyChange;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final ShardAssignmentRegistry registry;
    private final AssignmentSplitPlanner splitPlanner;

    @PostConstruct
    public void initialize() {
        refreshAssignments();
    }

    public void refreshAssignments() {
        registry.findAll().forEach(existing -> registry.delete(existing.id()));
    }

    public AssignmentChangeSet applySplit(SplitOperation change) {
        ShardAssignment original = registry.findSingleByShard(change.sourceShard());
        List<ShardAssignment> children = splitPlanner.split(original, change);
        registry.delete(original.id());
        children.forEach(registry::save);
        return new AssignmentChangeSet(original, children);
    }
}
