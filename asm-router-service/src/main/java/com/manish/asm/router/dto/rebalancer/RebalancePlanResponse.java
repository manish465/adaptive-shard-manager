package com.manish.asm.router.dto.rebalancer;

import com.manish.asm.router.model.RebalanceAction;
import com.manish.asm.router.model.RebalanceReason;

public record RebalancePlanResponse(
        String shardName,
        RebalanceReason reason,
        RebalanceAction action,
        int priority
) {
}
