package com.manish.asm.router.migration;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class MigrationTaskGenerator {
    public List<MigrationTask> generate(List<MigrationPlan> plans) {
        return plans.stream().map(this::toTask).toList();
    }

    private MigrationTask toTask(MigrationPlan plan) {
        return new MigrationTask(
                UUID.randomUUID(),
                plan.operationId(),
                plan.id(),
                plan.sourceShard(),
                plan.targetShard(),
                plan.startToken(),
                plan.endToken(),
                MigrationTaskStatus.PENDING,
                LocalDateTime.now()
        );
    }
}
