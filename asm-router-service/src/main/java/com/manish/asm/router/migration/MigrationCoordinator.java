package com.manish.asm.router.migration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MigrationCoordinator {
    private final MigrationTaskRegistry registry;

    public boolean isOperationComplete(UUID operationId) {
        return registry.findByOperation(operationId)
                .stream()
                .allMatch(task -> task.status() == MigrationTaskStatus.COMPLETED);
    }
}
