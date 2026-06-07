package com.manish.asm.router.migration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MigrationService {
    private final MigrationTaskGenerator generator;
    private final MigrationTaskRegistry registry;

    public void createTasks(List<MigrationPlan> plans) {
        generator.generate(plans).forEach(registry::save);
    }

    public List<MigrationTask> tasks() {
        return registry.findAll();
    }
}
