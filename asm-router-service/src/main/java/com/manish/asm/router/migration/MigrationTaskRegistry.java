package com.manish.asm.router.migration;

import com.manish.asm.router.repository.MigrationTaskRepository;
import com.manish.asm.router.repository.entity.MigrationTaskEntity;
import com.manish.asm.router.repository.mapper.MigrationTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MigrationTaskRegistry {
    private final MigrationTaskRepository repository;
    private final MigrationTaskMapper mapper;

    public void save(MigrationTask task) {
        MigrationTaskEntity entity = mapper.toEntity(task);
        repository.save(entity);
    }

    public MigrationTask findById(UUID id) {

        return repository.findById(id)
                .map(mapper::toDomain)
                .orElse(null);
    }

    public List<MigrationTask> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    public void update(MigrationTask task) {

        MigrationTaskEntity entity =
                mapper.toEntity(task);

        repository.save(entity);
    }

    public List<MigrationTask> findByOperation(UUID operationId) {

        return repository.findByOperationId(operationId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
