package com.manish.asm.router.dto.rebalancer;

import com.manish.asm.router.model.PlanStatus;
import com.manish.asm.router.model.RebalanceAction;
import com.manish.asm.router.model.RebalanceReason;

import java.time.LocalDateTime;
import java.util.UUID;

public record RebalancePlanView(
        UUID id,
        String shardName,
        RebalanceReason reason,
        RebalanceAction action,
        int priority,
        PlanStatus status,
        LocalDateTime createdAt
) {
}
