package com.manish.asm.router.dto.operation;

import com.manish.asm.router.recovery.RecoveryState;
import com.manish.asm.router.topology.OperationType;
import com.manish.asm.router.topology.SplitOperationStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OperationDetailsResponse(
        UUID operationId,
        OperationType type,
        String sourceShard,
        List<String> targetShards,
        SplitOperationStatus status,
        RecoveryState recoveryState,
        int totalTasks,
        int completedTasks,
        LocalDateTime createdAt
) {
}
