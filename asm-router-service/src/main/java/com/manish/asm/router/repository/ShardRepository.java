package com.manish.asm.router.repository;

import com.manish.asm.router.model.Shard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShardRepository extends JpaRepository<Shard, UUID> {
}
