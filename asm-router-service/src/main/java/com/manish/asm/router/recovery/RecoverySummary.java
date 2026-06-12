package com.manish.asm.router.recovery;

public record RecoverySummary(
        int totalOperations,
        int completed,
        int inProgress,
        int failed,
        int inconsistent
) {
}
