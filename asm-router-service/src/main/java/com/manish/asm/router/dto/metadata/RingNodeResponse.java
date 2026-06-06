package com.manish.asm.router.dto.metadata;

import java.util.List;

public record RingNodeResponse(
        String shard,
        List<Long> hashList
) {
}
