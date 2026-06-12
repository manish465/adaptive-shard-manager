package com.manish.asm.router.recovery;

public record RecoveryResult(
        RecoveryState state,
        int totalTasks,
        int completedTasks
) {
}
