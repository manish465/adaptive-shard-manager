package com.manish.asm.router.migration;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MigrationTaskRegistry {
    private final ConcurrentHashMap<UUID, MigrationTask> tasks = new ConcurrentHashMap<>();

    public void save(MigrationTask task) {
        tasks.put(task.id(), task);
    }

    public MigrationTask findById(UUID id) {
        return tasks.get(id);
    }

    public List<MigrationTask> findAll() {
        return tasks.values().stream().toList();
    }

    public void update(MigrationTask task) {
        tasks.put(task.id(), task);
    }

    public List<MigrationTask> findByOperation(UUID operationId) {
        return tasks.values()
                .stream()
                .filter(task -> task.operationId().equals(operationId))
                .toList();
    }
}
