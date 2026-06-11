package com.manish.asm.router.repository;

import com.manish.asm.router.repository.entity.SplitOperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SplitOperationRepository extends JpaRepository<SplitOperationEntity, UUID> {
}
