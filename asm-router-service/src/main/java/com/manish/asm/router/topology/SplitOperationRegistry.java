package com.manish.asm.router.topology;

import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SplitOperationRegistry {
    private final ConcurrentHashMap<UUID, TopologyChange> operations = new ConcurrentHashMap<>();

    public void save(TopologyChange change) {
        operations.put(change.operationId(), change);
    }

    public TopologyChange find(UUID operationId) {
        return operations.get(operationId);
    }

    public void remove(UUID operationId) {
        operations.remove(operationId);
    }
}
