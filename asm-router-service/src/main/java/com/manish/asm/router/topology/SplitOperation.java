package com.manish.asm.router.topology;

import java.time.LocalDateTime;
import java.util.UUID;

public record SplitOperation(
        UUID id,
        String sourceShard,
        String childShard1,
        String childShard2,
        LocalDateTime createdAt
) {
}
