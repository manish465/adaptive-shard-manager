package com.manish.asm.router.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record RebalancePlan(
        UUID id,
        String shardName,
        RebalanceReason reason,
        RebalanceAction action,
        int priority,
        PlanStatus status,
        LocalDateTime createdAt
) {
}
