package com.manish.asm.router.repository.mapper;

import com.manish.asm.router.migration.MigrationTask;
import com.manish.asm.router.repository.entity.MigrationTaskEntity;
import org.springframework.stereotype.Component;

@Component
public class MigrationTaskMapper {
    public MigrationTaskEntity toEntity(MigrationTask task) {
        return MigrationTaskEntity.builder()
                .id(task.id())
                .operationId(task.operationId())
                .planId(task.planId())
                .sourceShard(task.sourceShard())
                .targetShard(task.targetShard())
                .startToken(task.startToken())
                .endToken(task.endToken())
                .status(task.status())
                .createdAt(task.createdAt())
                .build();
    }

    public MigrationTask toDomain(MigrationTaskEntity entity) {
        return new MigrationTask(
                entity.getId(),
                entity.getOperationId(),
                entity.getPlanId(),
                entity.getSourceShard(),
                entity.getTargetShard(),
                entity.getStartToken(),
                entity.getEndToken(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }
}
