package com.manish.asm.router.controller;

import com.manish.asm.router.recovery.RecoveryQueryService;
import com.manish.asm.router.recovery.RecoverySummary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recovery")
@RequiredArgsConstructor
public class RecoveryController {
    private final RecoveryQueryService recoveryQueryService;

    @GetMapping("/summary")
    public RecoverySummary getSummary() {
        return recoveryQueryService.getSummary();
    }
}
