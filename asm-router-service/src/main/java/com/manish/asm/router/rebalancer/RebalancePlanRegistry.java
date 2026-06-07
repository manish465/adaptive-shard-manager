package com.manish.asm.router.rebalancer;

import com.manish.asm.router.model.RebalancePlan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RebalancePlanRegistry {
    private final ConcurrentHashMap<UUID, RebalancePlan> plans = new ConcurrentHashMap<>();

    public void save(RebalancePlan plan) {
        plans.put(plan.id(), plan);
    }

    public List<RebalancePlan> getAll() {
        return plans
                .values()
                .stream()
                .sorted((a, b) -> Integer.
                        compare(b.priority(),a.priority())
                )
                .toList();
    }

    public void clear() {
        plans.clear();
    }

    public RebalancePlan findById(UUID planId) {
        return plans.get(planId);
    }

    public void saveOrUpdate(RebalancePlan plan) {
        plans.put(plan.id(), plan);
    }
}
