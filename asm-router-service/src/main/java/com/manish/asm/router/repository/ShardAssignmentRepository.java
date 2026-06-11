package com.manish.asm.router.repository;

import com.manish.asm.router.repository.entity.ShardAssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ShardAssignmentRepository extends JpaRepository<ShardAssignmentEntity, UUID> {
    List<ShardAssignmentEntity> findByShardName(String shardName);
}
