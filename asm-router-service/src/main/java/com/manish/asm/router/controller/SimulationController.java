package com.manish.asm.router.controller;

import com.manish.asm.router.dto.simulation.RebalanceSimulationResponse;
import com.manish.asm.router.model.Shard;
import com.manish.asm.router.model.ShardStatus;
import com.manish.asm.router.repository.ShardRepository;
import com.manish.asm.router.simulation.RebalanceSimulator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/simulation")
@RequiredArgsConstructor
public class SimulationController {
    private final ShardRepository shardRepository;
    private final RebalanceSimulator simulator;

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
}
