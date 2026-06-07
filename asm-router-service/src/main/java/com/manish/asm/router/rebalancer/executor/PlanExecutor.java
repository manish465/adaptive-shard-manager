package com.manish.asm.router.rebalancer.executor;

import com.manish.asm.router.model.RebalancePlan;
import com.manish.asm.router.topology.TopologyChange;

public interface PlanExecutor {
    boolean supports(RebalancePlan plan);
    TopologyChange execute(RebalancePlan plan);
}
