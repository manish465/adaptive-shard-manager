package com.manish.asm.router.dto.shard;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateShardRequest {
    @NotBlank
    private String shardName;
    @NotBlank
    private String databaseUrl;
}
