package com.manish.asm.router.migration;

import java.util.UUID;

public record MigrationPlan(
        UUID id,
        UUID operationId,
        String sourceShard,
        String targetShard,
        long startToken,
        long endToken
) {
}
