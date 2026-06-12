package com.manish.asm.router.recovery;

import com.manish.asm.router.topology.SplitOperation;
import com.manish.asm.router.topology.SplitOperationRegistry;
import com.manish.asm.router.topology.SplitOperationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecoveryActionExecutor {
    private final SplitOperationRegistry splitOperationRegistry;

    public void execute(RecoveryAction action, SplitOperation operation) {
        switch (action) {
            case MARK_OPERATION_COMPLETED -> markCompleted(operation);
            case ALERT_OPERATOR -> alertOperator(operation);
            case NO_ACTION -> log.debug("No recovery action required for operation {}", operation.operationId());
        }
    }

    private void markCompleted(SplitOperation operation) {
        log.info("Marking recovered operation {} as COMPLETED", operation.operationId());
        splitOperationRegistry.updateStatus(operation.operationId(), SplitOperationStatus.COMPLETED);
    }

    private void alertOperator(SplitOperation operation) {
        log.warn("Recovery requires manual investigation. operationId={}", operation.operationId());
    }
}
