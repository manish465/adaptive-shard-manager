package com.manish.asm.router.metadata;

import com.manish.asm.router.model.ShardAssignment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ShardAssignmentRegistry {
    private final List<ShardAssignment> assignments = new CopyOnWriteArrayList<>();

    public List<ShardAssignment> getAssignments() {
        return List.copyOf(assignments);
    }

    public void replaceAll(List<ShardAssignment> newAssignments) {
        assignments.clear();
        assignments.addAll(newAssignments);
    }
}
