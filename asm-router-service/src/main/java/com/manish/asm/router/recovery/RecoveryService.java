package com.manish.asm.router.recovery;

import com.manish.asm.router.migration.MigrationTask;
import com.manish.asm.router.migration.MigrationTaskRegistry;
import com.manish.asm.router.topology.SplitOperation;
import com.manish.asm.router.topology.SplitOperationRegistry;
import com.manish.asm.router.topology.SplitOperationStatus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecoveryService {
    private final SplitOperationRegistry splitOperationRegistry;
    private final MigrationTaskRegistry migrationTaskRegistry;
    private final RecoveryAnalyzer recoveryAnalyzer;
    private final RecoveryActionResolver recoveryActionResolver;
    private final RecoveryActionExecutor recoveryActionExecutor;

    @PostConstruct
    public void recover() {
        log.info("Starting ASM recovery scan");
        List<SplitOperation> operations = splitOperationRegistry.findAll();
        operations.stream()
                .filter(operation -> operation.status() == SplitOperationStatus.IN_PROGRESS)
                .forEach(this::inspectOperation);
        log.info("ASM recovery scan completed");
    }

    private void inspectOperation(SplitOperation operation) {
        List<MigrationTask> tasks = migrationTaskRegistry.findByOperation(operation.operationId());
        RecoveryResult result = recoveryAnalyzer.analyze(tasks);

        log.info(
                """
                Recovery inspection:
                operationId={}
                operationComplete={}
                totalTasks={}
                completedTasks={}
                """,
                operation.operationId(),
                result.state(),
                result.totalTasks(),
                result.completedTasks()
        );

        RecoveryAction action = recoveryActionResolver.resolve(result.state());
        recoveryActionExecutor.execute(action, operation);
    }
}
