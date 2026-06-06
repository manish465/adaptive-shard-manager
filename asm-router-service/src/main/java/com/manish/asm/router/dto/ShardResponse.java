package com.manish.asm.router.dto;

import com.manish.asm.router.model.ShardStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ShardResponse(
        UUID id,
        String shardName,
        ShardStatus status,
        String databaseUrl,
        LocalDateTime createdAt
) {
}
