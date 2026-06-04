package com.manish.asm.router.controller;

import com.manish.asm.router.dto.RoutingSimulationResponse;
import com.manish.asm.router.model.Shard;
import com.manish.asm.router.service.RoutingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/router")
@RequiredArgsConstructor
public class RoutingController {
    private final RoutingService routingService;

    @GetMapping
    public String route(@RequestParam String key) {
        Shard shard = routingService.route(key);
        return shard.getShardName();
    }

    @PostMapping("/simulate")
    public RoutingSimulationResponse simulate(@RequestParam(defaultValue = "10000") int keys) {
        return routingService.simulate(keys);
    }
}
