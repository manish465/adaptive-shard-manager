package com.manish.asm.router.recovery;

import com.manish.asm.router.migration.MigrationTask;
import com.manish.asm.router.migration.MigrationTaskRegistry;
import com.manish.asm.router.topology.SplitOperation;
import com.manish.asm.router.topology.SplitOperationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecoveryQueryService {
    private final SplitOperationRegistry operationRegistry;
    private final MigrationTaskRegistry migrationTaskRegistry;
    private final RecoveryAnalyzer recoveryAnalyzer;

    public RecoverySummary getSummary() {
        List<SplitOperation> operations = operationRegistry.findAll();

        int completed = 0;
        int inProgress = 0;
        int failed = 0;
        int inconsistent = 0;

        for (SplitOperation operation : operations) {
            List<MigrationTask> tasks = migrationTaskRegistry.findByOperation(operation.operationId());
            RecoveryResult result = recoveryAnalyzer.analyze(tasks);

            switch (result.state()) {
                case COMPLETED -> completed++;
                case IN_PROGRESS -> inProgress++;
                case FAILED -> failed++;
                case INCONSISTENT -> inconsistent++;
            }
        }

        return new RecoverySummary(
                operations.size(),
                completed,
                inProgress,
                failed,
                inconsistent
        );
    }
}
