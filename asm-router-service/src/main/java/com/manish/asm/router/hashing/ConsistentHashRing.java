package com.manish.asm.router.hashing;

import com.manish.asm.router.config.ShardingProperties;
import com.manish.asm.router.model.Shard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsistentHashRing {
    private final HashFunction hashFunction;
    private final SortedMap<Long, Shard> ring = new TreeMap<>();

    public void buildRing(Collection<Shard> shards) {
        ring.clear();

        for (Shard shard : shards) {
            for(int i = 0; i < ShardingProperties.VIRTUAL_NODES; i++) {
                String vNodeName = shard.getShardName() + "#" + i;
                long hash = hashFunction.hash(vNodeName);
                log.info("ConsistentHashRing.buildRing with hash vNodeName {} for hash {} for shard {}", vNodeName, hash, shard);
                ring.put(hash, shard);
            }
        }
    }

    public Shard locate(String key) {
        if (ring.isEmpty()) throw new IllegalStateException("Ring is empty");

        long hash = hashFunction.hash(key);
        log.info("ConsistentHashRing.locate with hash value {} for key {}", hash, key);
        SortedMap<Long, Shard> tailMap = ring.tailMap(hash);
        log.info("ConsistentHashRing.locate rind {}", tailMap);

        Long targetHash;

        if (tailMap.isEmpty()) targetHash = ring.firstKey();
        else targetHash = tailMap.firstKey();
        log.info("ConsistentHashRing.locate targetHash {}", targetHash);

        return ring.get(targetHash);
    }

    public int ringSize() {
        return ring.size();
    }
}
