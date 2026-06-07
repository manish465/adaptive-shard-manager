package com.manish.asm.router.metadata;

import com.manish.asm.router.model.ShardAssignment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ShardAssignmentRegistry {
    private final ConcurrentHashMap<UUID, ShardAssignment> assignments = new ConcurrentHashMap<>();

    public void save(ShardAssignment assignment) {
        assignments.put(assignment.id(), assignment);
    }

    public void delete(UUID assignmentId) {
        assignments.remove(assignmentId);
    }

    public List<ShardAssignment> findAll() {
        return assignments.values()
                .stream()
                .toList();
    }

    public List<ShardAssignment> findByShard(String shardName) {

        return assignments.values()
                .stream()
                .filter(it -> it.shardName().equals(shardName))
                .toList();
    }
}
