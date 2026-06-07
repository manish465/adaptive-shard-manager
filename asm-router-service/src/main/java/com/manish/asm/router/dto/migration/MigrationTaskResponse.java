package com.manish.asm.router.dto.migration;

import com.manish.asm.router.migration.MigrationTaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record MigrationTaskResponse(
        UUID id,
        String sourceShard,
        String targetShard,
        long startToken,
        long endToken,
        MigrationTaskStatus status,
        LocalDateTime createdAt
) {
}
