package com.manish.asm.router.metadata;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final ShardAssignmentRegistry registry;

    @PostConstruct
    public void initialize() {
        refreshAssignments();
    }

    public void refreshAssignments() {
        registry.findAll().forEach(existing -> registry.delete(existing.id()));
    }
}
