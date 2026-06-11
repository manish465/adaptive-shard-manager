package com.manish.asm.router.topology;

import com.manish.asm.router.model.SplitOperationEntity;
import com.manish.asm.router.repository.SplitOperationMapper;
import com.manish.asm.router.repository.SplitOperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SplitOperationRegistry {
    private final SplitOperationRepository repository;
    private final SplitOperationMapper mapper;

    public SplitOperation save(SplitOperation operation) {
        SplitOperationEntity entity = mapper.toEntity(operation);
        SplitOperationEntity saved = repository.save(entity);

        return mapper.toDomain(saved);
    }

    public Optional<SplitOperation> findById(UUID operationId) {
        return repository.findById(operationId).map(mapper::toDomain);
    }

    public List<SplitOperation> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    public void updateStatus(UUID operationId, SplitOperationStatus status) {
        repository.findById(operationId).ifPresent(entity -> {
            entity.setStatus(status);
            repository.save(entity);
        });
    }

    public void delete(UUID operationId) {
        repository.deleteById(operationId);
    }
}
