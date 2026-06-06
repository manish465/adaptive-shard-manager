package com.manish.asm.router.metrics;

import com.manish.asm.router.model.ShardMetrics;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class MetricsSimulator {
    public ShardMetrics generate() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        return new ShardMetrics(
                random.nextDouble(10, 100),
                random.nextDouble(10, 100),
                random.nextDouble(10, 100),
                random.nextLong(100, 10000)
        );
    }
}
