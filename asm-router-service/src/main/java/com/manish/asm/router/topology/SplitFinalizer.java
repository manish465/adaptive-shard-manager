package com.manish.asm.router.topology;

import com.manish.asm.router.repository.entity.Shard;
import com.manish.asm.router.model.ShardStatus;
import com.manish.asm.router.repository.ShardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SplitFinalizer {
    private final ShardRepository shardRepository;
    private final SplitOperationRegistry splitOperationRegistry;

    public void finalizeSplit(SplitOperation change) {
        Shard source = shardRepository
                        .findByShardName(change.sourceShard())
                        .orElseThrow();

        if (source.getStatus() == ShardStatus.INACTIVE) {
            log.info("Split already finalized for shard {}", source.getShardName());
            return;
        }

        source.setStatus(ShardStatus.INACTIVE);
        splitOperationRegistry.updateStatus(change.operationId(), SplitOperationStatus.COMPLETED);
        shardRepository.save(source);
    }
}
