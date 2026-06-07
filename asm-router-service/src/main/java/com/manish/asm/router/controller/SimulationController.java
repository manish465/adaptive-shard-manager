package com.manish.asm.router.controller;

import com.manish.asm.router.dto.migration.MigrationPlanResponse;
import com.manish.asm.router.dto.simulation.RebalanceSimulationResponse;
import com.manish.asm.router.migration.MigrationPlanner;
import com.manish.asm.router.model.Shard;
import com.manish.asm.router.model.ShardAssignment;
import com.manish.asm.router.model.ShardStatus;
import com.manish.asm.router.repository.ShardRepository;
import com.manish.asm.router.simulation.RebalanceSimulator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/simulation")
@RequiredArgsConstructor
public class SimulationController {
    private final ShardRepository shardRepository;
    private final RebalanceSimulator simulator;
    private final MigrationPlanner migrationPlanner;

    @PostMapping("/add-shard")
    public RebalanceSimulationResponse simulateAddShard(
            @RequestParam(defaultValue = "100000") int keys
    ) {
        List<Shard> shards = shardRepository.findAll();
        Shard newShard = new Shard(
                UUID.randomUUID(),
                "simulated-shard",
                ShardStatus.ACTIVE,
                "simulation",
                LocalDateTime.now()
        );

        return simulator.simulateAddShard(
                shards,
                newShard,
                keys
        );
    }

    @GetMapping("/migration/split")
    public List<MigrationPlanResponse> simulateSplit() {
        ShardAssignment assignment = new ShardAssignment(UUID.randomUUID(),"shard-a", 0, 333332);

        return migrationPlanner
                .planSplit(assignment, "shard-a-1", "shard-a-2")
                .stream()
                .map(plan -> new MigrationPlanResponse(
                    plan.sourceShard(),
                    plan.targetShard(),
                    plan.startToken(),
                    plan.endToken()
                ))
                .toList();
    }
}
