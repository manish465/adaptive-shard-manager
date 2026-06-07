package com.manish.asm.router.model;

public record ShardAssignment(
        String shardName,
        long startToken,
        long endToken
) {
}
