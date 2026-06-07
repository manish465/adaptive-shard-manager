package com.manish.asm.router.controller;

import com.manish.asm.router.dto.migration.MigrationTaskResponse;
import com.manish.asm.router.migration.MigrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/migration")
@RequiredArgsConstructor
public class MigrationController {
    private final MigrationService service;

    @GetMapping("/tasks")
    public List<MigrationTaskResponse> tasks() {
        return service.tasks()
                .stream()
                .map(task -> new MigrationTaskResponse(
                    task.id(),
                    task.sourceShard(),
                    task.targetShard(),
                    task.startToken(),
                    task.endToken(),
                    task.status(),
                    task.createdAt()
                ))
                .toList();
    }
}
