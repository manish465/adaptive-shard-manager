package com.manish.asm.router.topology;

import com.manish.asm.router.model.Shard;
import com.manish.asm.router.model.ShardStatus;
import com.manish.asm.router.repository.ShardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SplitFinalizer {
    private final ShardRepository shardRepository;

    public void finalizeSplit(TopologyChange change) {
        Shard source = shardRepository
                        .findByShardName(change.sourceShard())
                        .orElseThrow();

        source.setStatus(ShardStatus.INACTIVE);
        shardRepository.save(source);
    }
}
