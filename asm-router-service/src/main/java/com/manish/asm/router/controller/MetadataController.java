package com.manish.asm.router.controller;

import com.manish.asm.router.metadata.ShardRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MetadataController {
    private final ShardRegistry registry;

    @GetMapping("/api/v1/metadata/ring")
    public int ringSize() {
        return registry.ringSize();
    }
}
