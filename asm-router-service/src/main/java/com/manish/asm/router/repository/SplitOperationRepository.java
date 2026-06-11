package com.manish.asm.router.repository;

import com.manish.asm.router.model.SplitOperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SplitOperationRepository extends JpaRepository<SplitOperationEntity, UUID> {
}
