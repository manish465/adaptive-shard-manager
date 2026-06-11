package com.manish.asm.router.controller;

import com.manish.asm.router.dto.migration.MigrationPlanResponse;
import com.manish.asm.router.dto.simulation.RebalanceSimulationResponse;
import com.manish.asm.router.metadata.AssignmentChangeSet;
import com.manish.asm.router.migration.MigrationPlanner;
import com.manish.asm.router.model.Shard;
import com.manish.asm.router.model.ShardAssignment;
import com.manish.asm.router.model.ShardStatus;
import com.manish.asm.router.repository.ShardRepository;
import com.manish.asm.router.topology.SplitOperation;
import com.manish.asm.router.simulation.RebalanceSimulator;
import com.manish.asm.router.topology.TopologyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/simulation")
@RequiredArgsConstructor
public class SimulationController {
    private final TopologyService topologyService;
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

        return simulator.simulateAddShard(shards, newShard, keys);
    }

    @GetMapping("/migration/split")
    public List<MigrationPlanResponse> simulateSplit() {
        ShardAssignment original = new ShardAssignment(UUID.randomUUID(),"shard-a", 0, 333332);
        ShardAssignment childShard1 = new ShardAssignment(UUID.randomUUID(),"shard-a-1", 0, 166666);
        ShardAssignment childShard2 = new ShardAssignment(UUID.randomUUID(),"shard-a-2", 166667, 333332);
        AssignmentChangeSet changeSet = new AssignmentChangeSet(original, List.of(childShard1, childShard2));
        SplitOperation change = topologyService.splitShard("shard-a");

        return migrationPlanner.planSplit(changeSet, change).plans()
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
