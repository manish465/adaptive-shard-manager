package com.manish.asm.router.migration;

public record MigrationPlan(
        String sourceShard,
        String targetShard,
        long startToken,
        long endToken
) {
}
