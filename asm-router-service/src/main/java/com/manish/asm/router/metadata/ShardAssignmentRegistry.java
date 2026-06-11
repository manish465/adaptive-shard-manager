package com.manish.asm.router.metadata;

import com.manish.asm.router.model.ShardAssignment;
import com.manish.asm.router.repository.ShardAssignmentRepository;
import com.manish.asm.router.repository.mapper.ShardAssignmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ShardAssignmentRegistry {
    private final ShardAssignmentRepository repository;
    private final ShardAssignmentMapper mapper;

    public void save(ShardAssignment assignment) {
        repository.save(mapper.toEntity(assignment));
    }

    public void saveAll(Collection<ShardAssignment> shardAssignments) {
        repository.saveAll(shardAssignments.stream().map(mapper::toEntity).toList());
    }

    public void delete(UUID assignmentId) {
        repository.deleteById(assignmentId);
    }

    public List<ShardAssignment> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    public List<ShardAssignment> findByShard(String shardName) {
        return repository.findByShardName(shardName)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    public ShardAssignment findSingleByShard(String shardName) {
        return repository.findByShardName(shardName)
                .stream()
                .findFirst()
                .map(mapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found for shard: " + shardName));
    }
}
