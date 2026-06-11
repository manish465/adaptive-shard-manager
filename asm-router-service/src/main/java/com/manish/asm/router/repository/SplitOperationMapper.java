package com.manish.asm.router.repository;

import com.manish.asm.router.model.SplitOperationEntity;
import com.manish.asm.router.topology.SplitOperation;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SplitOperationMapper {
    private static final String DELIMITER = ",";

    public SplitOperationEntity toEntity(SplitOperation operation) {
        SplitOperationEntity entity = new SplitOperationEntity();

        entity.setOperationId(operation.operationId());
        entity.setType(operation.type());
        entity.setSourceShard(operation.sourceShard());
        entity.setTargetShards(String.join(DELIMITER, operation.targetShards()));
        entity.setStatus(operation.status());
        entity.setCreatedAt(operation.createdAt());

        return entity;
    }

    public SplitOperation toDomain(SplitOperationEntity entity) {
        List<String> targetShards = entity.getTargetShards() == null
                ? List.of()
                : Arrays.stream(entity.getTargetShards().split(DELIMITER))
                .map(String::trim)
                .toList();

        return new SplitOperation(
                entity.getOperationId(),
                entity.getType(),
                entity.getSourceShard(),
                targetShards,
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }
}
