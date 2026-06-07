package com.manish.asm.router.rebalancer.executor;

import com.manish.asm.router.model.RebalancePlan;

public interface PlanExecutor {
    boolean supports(RebalancePlan plan);
    void execute(RebalancePlan plan);
}
