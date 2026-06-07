package com.manish.asm.router.dto.migration;

public record MigrationPlanResponse(
        String sourceShard,
        String targetShard,
        long startToken,
        long endToken
) {
}
