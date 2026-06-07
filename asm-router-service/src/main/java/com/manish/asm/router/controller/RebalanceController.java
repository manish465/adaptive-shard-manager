package com.manish.asm.router.controller;

import com.manish.asm.router.dto.rebalancer.RebalancePlanResponse;
import com.manish.asm.router.model.RebalancePlan;
import com.manish.asm.router.rebalancer.RebalancePlanRegistry;
import com.manish.asm.router.rebalancer.RebalancePlanner;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rebalance")
@RequiredArgsConstructor
public class RebalanceController {
    private final RebalancePlanner planner;
    private final RebalancePlanRegistry registry;

    @GetMapping("/plan")
    public List<RebalancePlanResponse> plan() {
        return planner.generatePlan();
    }

    @PostMapping("/test")
    public String createTestPlan() {
        planner.createTestPlan();
        return "plan created";
    }

    @GetMapping("/plans")
    public List<RebalancePlan> plans() {
        return registry.getAll();
    }
}
