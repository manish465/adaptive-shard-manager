package com.manish.asm.router.controller;

import com.manish.asm.router.dto.migration.MigrationTaskResponse;
import com.manish.asm.router.migration.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/migration")
@RequiredArgsConstructor
public class MigrationController {
    private final MigrationService service;
    private final MigrationExecutor executor;
    private final MigrationTaskRegistry taskRegistry;

    @GetMapping("/tasks")
    public List<MigrationTaskResponse> tasks() {
        return service.tasks()
                .stream()
                .map(task -> new MigrationTaskResponse(
                    task.id(),
                    task.planId(),
                    task.sourceShard(),
                    task.targetShard(),
                    task.startToken(),
                    task.endToken(),
                    task.status(),
                    task.createdAt()
                ))
                .toList();
    }

    @PostMapping("/tasks/{taskId}/execute")
    public void executeTask(@PathVariable UUID taskId) {
        MigrationTask task = service.tasks()
                        .stream()
                        .filter(it -> it.id().equals(taskId))
                        .findFirst()
                        .orElseThrow();

        executor.executeTask(task);
    }

    @PostMapping("/tasks/execute-all")
    public void executeAll() {
        taskRegistry.findAll()
                .stream()
                .filter(task -> task.status() == MigrationTaskStatus.PENDING)
                .forEach(executor::executeTask);
    }
}
