package com.manish.asm.router.simulation;

import com.manish.asm.router.dto.RebalanceSimulationResponse;
import com.manish.asm.router.hashing.ConsistentHashRing;
import com.manish.asm.router.hashing.RingFactory;
import com.manish.asm.router.model.Shard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RebalanceSimulator {
    private final RingFactory ringFactory;

    public RebalanceSimulationResponse simulateAddShard(
            List<Shard> existingShards,
            Shard newShard,
            int keyCount
    ) {
        ConsistentHashRing oldRing = ringFactory.createRing();
        oldRing.buildRing(existingShards);

        List<Shard> futureShards = new ArrayList<>(existingShards);
        futureShards.add(newShard);

        ConsistentHashRing newRing = ringFactory.createRing();
        newRing.buildRing(futureShards);

        int moved = 0;

        for (int i = 0; i < keyCount; i++) {
            String key = "user-" + i;
            String oldShard = oldRing.locate(key).getShardName();
            String newShardName = newRing.locate(key).getShardName();

            if (!oldShard.equals(newShardName)) moved++;
        }

        double movementPercentage = ((double) moved / keyCount) * 100;

        return new RebalanceSimulationResponse(
                keyCount,
                moved,
                movementPercentage
        );
    }
}
