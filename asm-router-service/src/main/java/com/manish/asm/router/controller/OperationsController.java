package com.manish.asm.router.controller;

import com.manish.asm.router.topology.SplitOperation;
import com.manish.asm.router.topology.SplitOperationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/operations")
@RequiredArgsConstructor
public class OperationsController {
    private final SplitOperationRegistry splitOperationRegistry;

    @GetMapping
    public List<SplitOperation> getAllOperations() {
        return splitOperationRegistry.findAll().stream().toList();
    }

    @GetMapping("/{operationId}")
    public ResponseEntity<SplitOperation> getOperation(@PathVariable UUID operationId) {
        return ResponseEntity.ok(splitOperationRegistry.find(operationId));
    }
}
