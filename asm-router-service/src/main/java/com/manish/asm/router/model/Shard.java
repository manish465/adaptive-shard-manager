package com.manish.asm.router.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "shards")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Shard {
    @Id
    private UUID id;
    @Column(nullable = false)
    private String shardName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShardStatus status;
    @Column(nullable = false)
    private String databaseUrl;
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
