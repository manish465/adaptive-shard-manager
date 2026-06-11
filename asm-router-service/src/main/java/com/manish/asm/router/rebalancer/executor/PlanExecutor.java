package com.manish.asm.router.rebalancer.executor;

import com.manish.asm.router.model.RebalancePlan;
import com.manish.asm.router.topology.SplitOperation;

public interface PlanExecutor {
    boolean supports(RebalancePlan plan);
    SplitOperation execute(RebalancePlan plan);
}
