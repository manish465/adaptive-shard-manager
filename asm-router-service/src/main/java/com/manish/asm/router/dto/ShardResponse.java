package com.manish.asm.router.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ShardResponse(
        UUID id,
        String shardName,
        String status,
        String databaseUrl,
        LocalDateTime createdAt
) {
}
