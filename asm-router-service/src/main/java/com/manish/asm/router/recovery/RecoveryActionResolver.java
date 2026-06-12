package com.manish.asm.router.recovery;

import org.springframework.stereotype.Component;

@Component
public class RecoveryActionResolver {
    public RecoveryAction resolve(RecoveryState state) {
        return switch (state) {
            case COMPLETED -> RecoveryAction.FINALIZE_SPLIT;
            case FAILED, INCONSISTENT -> RecoveryAction.ALERT_OPERATOR;
            case IN_PROGRESS -> RecoveryAction.NO_ACTION;
        };
    }
}
