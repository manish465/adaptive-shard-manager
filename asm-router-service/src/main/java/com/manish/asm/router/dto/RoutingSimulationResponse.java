package com.manish.asm.router.dto;

import java.util.Map;

public record RoutingSimulationResponse(
        int totalKeys,
        int shardCount,
        long minLoad,
        long maxLoad,
        double imbalancePercent,
        Map<String, Long> distribution
) {
}
