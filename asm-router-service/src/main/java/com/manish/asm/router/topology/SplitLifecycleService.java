package com.manish.asm.router.topology;

import com.manish.asm.router.migration.MigrationCoordinator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SplitLifecycleService {
    private final MigrationCoordinator coordinator;
    private final SplitOperationRegistry registry;
    private final SplitFinalizer finalizer;

    public boolean finalizeIfReady(UUID operationId) {
        if (!coordinator.isOperationComplete(operationId)) return false;

        TopologyChange change = registry.find(operationId);

        if (change == null) return false;

        finalizer.finalizeSplit(change);
        registry.remove(operationId);

        return true;
    }
}
