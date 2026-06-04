package com.manish.asm.router.service;

import com.manish.asm.router.dto.RoutingSimulationResponse;
import com.manish.asm.router.metadata.ShardRegistry;
import com.manish.asm.router.model.Shard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RoutingService {
    private final ShardRegistry shardRegistry;

    public Shard route(String key) {
        return shardRegistry.locate(key);
    }

    public RoutingSimulationResponse simulate(int numberOfKeys) {
        Map<String, Long> distribution = new HashMap<>();

        for (int i = 0; i < numberOfKeys; i++) {
            String key = "user-" + i;
            Shard shard = route(key);
            distribution.merge(shard.getShardName(), 1L, Long::sum);
        }

        return new RoutingSimulationResponse(
                numberOfKeys,
                distribution.size(),
                distribution
        );
    }
}
