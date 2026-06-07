package com.manish.asm.router.controller;

import com.manish.asm.router.dto.shard.ShardAssignmentResponse;
import com.manish.asm.router.dto.shard.ShardTopologyResponse;
import com.manish.asm.router.metadata.ShardAssignmentRegistry;
import com.manish.asm.router.repository.ShardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topology")
@RequiredArgsConstructor
public class TopologyController {
    private final ShardRepository repository;
    private final ShardAssignmentRegistry assignmentRegistry;

    @GetMapping("/shards")
    public List<ShardTopologyResponse> shards(){
        return repository.findAll()
                .stream()
                .map(shard -> new ShardTopologyResponse(
                    shard.getShardName(),
                    shard.getStatus()
                ))
                .toList();
    }

    @GetMapping("/assignments")
    public List<ShardAssignmentResponse> assignments() {
        return assignmentRegistry
                .findAll()
                .stream()
                .map(assignment -> new ShardAssignmentResponse(
                    assignment.shardName(),
                    assignment.startToken(),
                    assignment.endToken()
                ))
                .toList();
    }
}
