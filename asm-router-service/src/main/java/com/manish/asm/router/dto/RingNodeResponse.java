package com.manish.asm.router.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class RingNodeResponse {
    private String shard;
    private List<Long> hashList;
}
