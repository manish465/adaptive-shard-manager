package com.manish.asm.router.controller;

import com.manish.asm.router.model.Shard;
import com.manish.asm.router.service.ShardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shards")
@RequiredArgsConstructor
public class ShardController {
    private final ShardService shardService;

    @PostMapping
    public Shard createShard(
            @RequestParam String shardName,
            @RequestParam String databaseUrl
    ) {
        return shardService.createShard(shardName, databaseUrl);
    }

    @GetMapping
    public List<Shard> getShards() {
        return shardService.getAllShards();
    }
}
