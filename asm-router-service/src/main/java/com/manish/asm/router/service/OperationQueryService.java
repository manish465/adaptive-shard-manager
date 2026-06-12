package com.manish.asm.router.service;

import com.manish.asm.router.dto.operation.OperationDetailsResponse;
import com.manish.asm.router.migration.MigrationTask;
import com.manish.asm.router.migration.MigrationTaskRegistry;
import com.manish.asm.router.recovery.RecoveryAnalyzer;
import com.manish.asm.router.recovery.RecoveryResult;
import com.manish.asm.router.topology.SplitOperation;
import com.manish.asm.router.topology.SplitOperationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperationQueryService {
    private final SplitOperationRegistry operationRegistry;
    private final MigrationTaskRegistry migrationTaskRegistry;
    private final RecoveryAnalyzer recoveryAnalyzer;

    public OperationDetailsResponse getOperation(UUID operationId) {
        SplitOperation operation = operationRegistry.findById(operationId).orElseThrow();
        List<MigrationTask> tasks = migrationTaskRegistry.findByOperation(operationId);
        RecoveryResult recoveryResult = recoveryAnalyzer.analyze(tasks);

        return new OperationDetailsResponse(
                operation.operationId(),
                operation.type(),
                operation.sourceShard(),
                operation.targetShards(),
                operation.status(),
                recoveryResult.state(),
                recoveryResult.totalTasks(),
                recoveryResult.completedTasks(),
                operation.createdAt()
        );
    }
}
