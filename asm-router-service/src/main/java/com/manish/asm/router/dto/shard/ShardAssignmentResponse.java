package com.manish.asm.router.dto.shard;

public record ShardAssignmentResponse(
        String shardName,
        long startToken,
        long endToken
) {
}
