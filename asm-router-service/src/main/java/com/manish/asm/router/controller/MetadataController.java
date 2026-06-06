package com.manish.asm.router.controller;

import com.manish.asm.router.dto.RingNodeResponse;
import com.manish.asm.router.dto.RingSummaryResponse;
import com.manish.asm.router.hashing.ConsistentHashRing;
import com.manish.asm.router.hashing.RingFactory;
import com.manish.asm.router.metadata.ShardRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/metadata")
public class MetadataController {
    private final ShardRegistry registry;
    private final ConsistentHashRing ring;

    public MetadataController(
            ShardRegistry registry,
            RingFactory ringFactory
    ) {
        this.registry = registry;
        this.ring = ringFactory.createRing();
    }

    @GetMapping("/ring-size")
    public int ringSize() {
        return registry.ringSize();
    }

    @GetMapping("/ring/nodes")
    public List<RingNodeResponse> getNodes() {
        return ring.getNodes();
    }

    @GetMapping("/ring")
    public RingSummaryResponse summary() {
        return registry.getSummary();
    }
}
