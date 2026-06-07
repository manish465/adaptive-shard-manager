package com.manish.asm.router.metadata;

import com.manish.asm.router.model.ShardAssignment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AssignmentInitializer {
    private final ShardAssignmentRegistry registry;

    @PostConstruct
    public void initialize() {
        registry.save(new ShardAssignment(UUID.randomUUID(), "shard-a", 0, 333332));
        registry.save(new ShardAssignment(UUID.randomUUID(), "shard-b", 333333, 666665));
        registry.save(new ShardAssignment(UUID.randomUUID(), "shard-c", 666666, 100000));
    }
}
