package com.manish.asm.router.metadata;

import com.manish.asm.router.model.ShardAssignment;

import java.util.List;

public record AssignmentChangeSet(
        ShardAssignment original,
        List<ShardAssignment> replacements
) {
}
