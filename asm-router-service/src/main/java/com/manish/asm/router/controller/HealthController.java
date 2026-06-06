package com.manish.asm.router.controller;

import com.manish.asm.router.dto.health.HotShardResponse;
import com.manish.asm.router.health.ShardHealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/health")
@RequiredArgsConstructor
public class HealthController {
    private final ShardHealthService service;

    @GetMapping("/hot-shards")
    public List<HotShardResponse> hotShards() {
        return service.findHotShards();
    }
}
