package com.manish.asm.router.controller;

import com.manish.asm.router.model.Shard;
import com.manish.asm.router.service.RoutingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
