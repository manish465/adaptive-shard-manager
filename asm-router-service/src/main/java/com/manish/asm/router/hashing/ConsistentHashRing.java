package com.manish.asm.router.hashing;

import com.manish.asm.router.config.ShardingProperties;
import com.manish.asm.router.dto.RingNodeResponse;
import com.manish.asm.router.model.Shard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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
                String vnode = shard.getShardName() + "#" + i;
                long hash = hashFunction.hash(vnode);
                log.info("ConsistentHashRing.buildRing with hash vNodeName {} for hash {} for shard {}", vnode, hash, shard.getShardName());
                ring.put(hash, shard);
                log.info("Ring range min={} max={}", ring.firstKey(), ring.lastKey());
            }
        }
    }

    public Shard locate(String key) {
        if (ring.isEmpty()) throw new IllegalStateException("Ring is empty");

        long hash = hashFunction.hash(key);
        log.info("key={} hash={} firstKey={} lastKey={}", key, hash, ring.firstKey(), ring.lastKey());
        log.info("ConsistentHashRing.locate with hash value {} for key {}", hash, key);
        SortedMap<Long, Shard> tailMap = ring.tailMap(hash);
        log.info("ConsistentHashRing.locate ring {}", tailMap);

        Long targetHash;

        if (tailMap.isEmpty()) targetHash = ring.firstKey();
        else targetHash = tailMap.firstKey();
        log.info("ConsistentHashRing.locate targetHash {}", targetHash);

        return ring.get(targetHash);
    }

    public int ringSize() {
        return ring.size();
    }

    public List<RingNodeResponse> getNodes() {
        return ring
                .entrySet()
                .stream()
                .collect(Collectors.groupingBy(
                        entry -> entry.getValue().getShardName(),
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())
                ))
                .entrySet()
                .stream()
                .map(node -> new RingNodeResponse(node.getKey(), node.getValue()))
                .collect(Collectors.toList());
    }
}
