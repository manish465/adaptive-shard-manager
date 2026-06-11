package com.manish.asm.router.repository.mapper;

import com.manish.asm.router.model.ShardAssignment;
import com.manish.asm.router.repository.entity.ShardAssignmentEntity;
import org.springframework.stereotype.Component;

@Component
public class ShardAssignmentMapper {
    public ShardAssignmentEntity toEntity(ShardAssignment assignment) {
        return ShardAssignmentEntity.builder()
                .id(assignment.id())
                .shardName(assignment.shardName())
                .startToken(assignment.startToken())
                .endToken(assignment.endToken())
                .build();
    }

    public ShardAssignment toDomain(ShardAssignmentEntity entity) {
        return new ShardAssignment(
                entity.getId(),
                entity.getShardName(),
                entity.getStartToken(),
                entity.getEndToken()
        );
    }
}
