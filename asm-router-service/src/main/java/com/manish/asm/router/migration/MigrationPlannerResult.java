package com.manish.asm.router.migration;

import java.util.List;

public record MigrationPlannerResult(
        List<MigrationPlan> plans
) {
}
