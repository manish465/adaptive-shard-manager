package com.manish.asm.router.model;

import java.util.UUID;

public record ShardAssignment(
        UUID id,
        String shardName,
        long startToken,
        long endToken
) {
}
