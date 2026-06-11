package com.manish.asm.router.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "shard_assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShardAssignmentEntity {
    @Id
    private UUID id;
    @Column(nullable = false)
    private String shardName;
    @Column(nullable = false)
    private long startToken;
    @Column(nullable = false)
    private long endToken;
}
