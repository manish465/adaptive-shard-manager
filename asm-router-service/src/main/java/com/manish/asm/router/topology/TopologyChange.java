package com.manish.asm.router.topology;

public record TopologyChange(
        String sourceShard,
        String childShard1,
        String childShard2
) {
}
