package com.manish.asm.router.topology;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SplitOperation(
        UUID operationId,
        OperationType type,
        String sourceShard,
        List<String> targetShards,
        SplitOperationStatus status,
        LocalDateTime createdAt
) {
}
