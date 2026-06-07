package com.manish.asm.router.migration;

import java.time.LocalDateTime;
import java.util.UUID;

public record MigrationTask(
        UUID id,
        String sourceShard,
        String targetShard,
        long startToken,
        long endToken,
        MigrationTaskStatus status,
        LocalDateTime createdAt
) {
}
