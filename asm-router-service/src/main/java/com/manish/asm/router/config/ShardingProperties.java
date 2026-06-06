package com.manish.asm.router.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "asm.hashing")
@Getter
@Setter
public class ShardingProperties {
    private int virtualNodes = 100;
}
