package com.manish.asm.router.controller;

import com.manish.asm.router.dto.shard.CreateShardRequest;
import com.manish.asm.router.dto.shard.ShardResponse;
import com.manish.asm.router.service.ShardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shards")
@RequiredArgsConstructor
public class ShardController {
    private final ShardService shardService;

    @PostMapping
    public ShardResponse createShard(
            @Valid @RequestBody CreateShardRequest createShardRequest
    ) {
        return shardService.createShard(
                        createShardRequest.getShardName(),
                        createShardRequest.getDatabaseUrl()
        );
    }

    @GetMapping
    public List<ShardResponse> getShards() {
        return shardService.getAllShards();
    }
}
