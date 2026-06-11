package com.manish.asm.router.repository.entity;

import com.manish.asm.router.migration.MigrationTaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "migration_tasks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MigrationTaskEntity {
    @Id
    private UUID id;
    @Column(nullable = false)
    private UUID operationId;
    @Column(nullable = false)
    private UUID planId;
    @Column(nullable = false)
    private String sourceShard;
    @Column(nullable = false)
    private String targetShard;
    @Column(nullable = false)
    private long startToken;
    @Column(nullable = false)
    private long endToken;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MigrationTaskStatus status;
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
