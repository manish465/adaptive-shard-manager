package com.manish.asm.router.metrics;

import com.manish.asm.router.repository.entity.Shard;
import com.manish.asm.router.repository.ShardRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MetricsRefresher {
    private final ShardRepository repository;
    private final MetricsSimulator simulator;
    private final MetricsRegistry registry;

    @PostConstruct
    public void initialize() {
        refreshMetrics();
    }

    @Scheduled(fixedRate = 10000)
    public void refreshMetrics() {
        for (Shard shard : repository.findAll()) {
            registry.put(shard.getShardName(), simulator.generate(shard));
        }
    }
}
