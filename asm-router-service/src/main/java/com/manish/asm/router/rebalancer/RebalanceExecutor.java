package com.manish.asm.router.rebalancer;

import com.manish.asm.router.model.PlanStatus;
import com.manish.asm.router.model.RebalancePlan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RebalanceExecutor {
    private final RebalancePlanRegistry registry;

    public void execute(UUID planId) {
        RebalancePlan existing = registry.findById(planId);
        if (existing == null) throw new IllegalArgumentException("Plan not found");

        RebalancePlan running = new RebalancePlan(
                        existing.id(),
                        existing.shardName(),
                        existing.reason(),
                        existing.action(),
                        existing.priority(),
                        PlanStatus.IN_PROGRESS,
                        existing.createdAt()
        );

        registry.saveOrUpdate(running);

        RebalancePlan completed = new RebalancePlan(
                        existing.id(),
                        existing.shardName(),
                        existing.reason(),
                        existing.action(),
                        existing.priority(),
                        PlanStatus.COMPLETED,
                        existing.createdAt()
        );

        registry.saveOrUpdate(completed);
    }
}
