package com.manish.asm.router.dto;

import java.util.Map;

public record RoutingSimulationResponse(
        int totalKeys,
        int shardCount,
        Map<String, Long> distribution
) {
}
