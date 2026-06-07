package com.manish.asm.router.metadata;

import com.manish.asm.router.model.ShardAssignment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AssignmentBootstrap {
    private final ShardAssignmentRegistry registry;

    @PostConstruct
    public void init() {
        registry.replaceAll(
                List.of(
                        new ShardAssignment("shard-a", 0, 333333),
                        new ShardAssignment("shard-b", 333334, 666666),
                        new ShardAssignment("shard-c", 666667, 999999)
                )
        );
    }
}
