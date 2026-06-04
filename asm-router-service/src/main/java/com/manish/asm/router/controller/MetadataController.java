package com.manish.asm.router.controller;

import com.manish.asm.router.hashing.ConsistentHashRing;
import com.manish.asm.router.metadata.ShardRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MetadataController {
    private final ShardRegistry registry;
    private final ConsistentHashRing consistentHashRing;

    @GetMapping("/api/v1/metadata/ring")
    public int ringSize() {
        return registry.ringSize();
    }

    @GetMapping("/api/v1/metadata/ring/nodes")
    public Map<Long, String> getNodes() {
        return consistentHashRing.getNodes();
    }
}
