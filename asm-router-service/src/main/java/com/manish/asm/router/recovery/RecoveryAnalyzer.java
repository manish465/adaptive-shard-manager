package com.manish.asm.router.recovery;

import com.manish.asm.router.migration.MigrationTask;
import com.manish.asm.router.migration.MigrationTaskStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecoveryAnalyzer {
    public RecoveryResult analyze(List<MigrationTask> tasks) {
        int totalTasks = tasks.size();
        int completedTasks = (int) tasks.stream()
                .filter(task -> task.status() == MigrationTaskStatus.COMPLETED)
                .count();
        boolean hasFailedTask = tasks
                .stream()
                .anyMatch(task -> task.status() == MigrationTaskStatus.FAILED);

        RecoveryState state;
        if (totalTasks == 0) state = RecoveryState.INCONSISTENT;
        else if (hasFailedTask) state = RecoveryState.FAILED;
        else if (completedTasks == totalTasks) state = RecoveryState.COMPLETED;
        else state = RecoveryState.IN_PROGRESS;

        return new RecoveryResult(state, totalTasks, completedTasks);
    }
}
