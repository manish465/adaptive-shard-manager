package com.manish.asm.router.topology;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SplitOperationRegistry {
    private final ConcurrentHashMap<UUID, SplitOperation> operations = new ConcurrentHashMap<>();

    public void save(SplitOperation operation) {
        operations.put(operation.operationId(), operation);
    }

    public SplitOperation find(UUID operationId) {
        return operations.get(operationId);
    }

    public Collection<SplitOperation> findAll() {
        return operations.values();
    }

    public void updateStatus(UUID operationId, SplitOperationStatus status) {

        operations.computeIfPresent(operationId, (id, existing) -> new SplitOperation(
            existing.operationId(),
            existing.type(),
            existing.sourceShard(),
            existing.targetShards(),
            status,
            existing.createdAt()
        ));
    }

    public void remove(UUID operationId) {
        operations.remove(operationId);
    }
}
