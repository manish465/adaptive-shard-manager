package com.manish.asm.router.dto;

public record RebalanceSimulationResponse(
        int totalKeys,
        int movedKeys,
        double movementPercentage
) {
}
