package com.manish.asm.router.controller;

import com.manish.asm.router.dto.rebalancer.RebalancePlanResponse;
import com.manish.asm.router.dto.rebalancer.RebalancePlanView;
import com.manish.asm.router.model.RebalancePlan;
import com.manish.asm.router.rebalancer.RebalanceExecutor;
import com.manish.asm.router.rebalancer.RebalancePlanRegistry;
import com.manish.asm.router.rebalancer.RebalancePlanner;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rebalance")
@RequiredArgsConstructor
public class RebalanceController {
    private final RebalancePlanner planner;
    private final RebalancePlanRegistry registry;
    private final RebalanceExecutor executor;

    @PostMapping("/generate")
    public List<RebalancePlanResponse> generate() {
        return planner.generatePlans();
    }

    @GetMapping("/plan")
    public List<RebalancePlanResponse> plan() {
        return planner.generatePlans();
    }

    @PostMapping("/test")
    public List<RebalancePlan> createTestPlan() {
        return planner.createTestPlan();
    }

    @GetMapping("/plans")
    public List<RebalancePlanView> plans() {
        return registry.getAll()
                .stream()
                .map(plan ->
                        new RebalancePlanView(
                                plan.id(),
                                plan.shardName(),
                                plan.reason(),
                                plan.action(),
                                plan.priority(),
                                plan.status(),
                                plan.createdAt()
                        ))
                .toList();
    }

    @PostMapping("/{planId}/execute")
    public void execute(@PathVariable UUID planId) {
        executor.execute(planId);
    }
}
