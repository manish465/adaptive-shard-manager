package com.manish.asm.router.migration;

import com.manish.asm.router.topology.SplitLifecycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MigrationExecutor {
    private final MigrationTaskRegistry registry;
    private final SplitLifecycleService lifecycleService;

    public void executeTask(MigrationTask task) {
        MigrationTask running = new MigrationTask(
            task.id(),
            task.operationId(),
            task.planId(),
            task.sourceShard(),
            task.targetShard(),
            task.startToken(),
            task.endToken(),
            MigrationTaskStatus.RUNNING,
            task.createdAt()
        );

        registry.update(running);

        try {
            Thread.sleep(1000);

            MigrationTask completed = new MigrationTask(
                task.id(),
                task.operationId(),
                task.planId(),
                task.sourceShard(),
                task.targetShard(),
                task.startToken(),
                task.endToken(),
                MigrationTaskStatus.COMPLETED,
                task.createdAt()
            );

            if(lifecycleService.finalizeIfReady(completed.operationId()))
                registry.update(completed);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            MigrationTask failed = new MigrationTask(
                task.id(),
                task.operationId(),
                task.planId(),
                task.sourceShard(),
                task.targetShard(),
                task.startToken(),
                task.endToken(),
                MigrationTaskStatus.FAILED,
                task.createdAt()
            );

            registry.update(failed);
        }
    }
}
