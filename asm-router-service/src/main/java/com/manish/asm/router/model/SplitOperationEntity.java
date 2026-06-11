package com.manish.asm.router.model;

import com.manish.asm.router.topology.OperationType;
import com.manish.asm.router.topology.SplitOperationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "split_operations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SplitOperationEntity {
    @Id
    private UUID operationId;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    private String sourceShard;
    @Column(nullable = false)
    private String targetShards;
    @Enumerated(EnumType.STRING)
    private SplitOperationStatus status;
    private LocalDateTime createdAt;
}
