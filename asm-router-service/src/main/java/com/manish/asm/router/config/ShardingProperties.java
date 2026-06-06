package com.manish.asm.router.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "asm.hashing")
public class ShardingProperties {
    private int virtualNodes = 100;
}
