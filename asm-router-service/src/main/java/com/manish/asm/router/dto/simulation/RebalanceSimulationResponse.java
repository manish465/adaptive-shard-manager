package com.manish.asm.router.dto.simulation;

public record RebalanceSimulationResponse(
        int totalKeys,
        int movedKeys,
        double movementPercentage
) {
}
