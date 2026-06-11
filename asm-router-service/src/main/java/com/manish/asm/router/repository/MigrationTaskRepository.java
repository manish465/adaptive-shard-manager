package com.manish.asm.router.repository;

import com.manish.asm.router.repository.entity.MigrationTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MigrationTaskRepository extends JpaRepository<MigrationTaskEntity, UUID> {
    List<MigrationTaskEntity> findByOperationId(UUID operationId);
}
