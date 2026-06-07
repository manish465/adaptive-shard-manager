package com.manish.asm.router.topology;

import java.util.UUID;

public record TopologyChange(
        UUID operationId,
        String sourceShard,
        String childShard1,
        String childShard2
) {
}
