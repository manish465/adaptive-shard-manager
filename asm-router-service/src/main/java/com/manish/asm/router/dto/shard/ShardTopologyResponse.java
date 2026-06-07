package com.manish.asm.router.dto.shard;

import com.manish.asm.router.model.ShardStatus;

public record ShardTopologyResponse(
        String shardName,
        ShardStatus status
) {
}
