package com.mobarenas.mavanity.rewards;

import com.mobarenas.mavanity.MaVanity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by HP1 on 12/29/2015.
 */
public class CratesQueue {

    private MaVanity plugin;
    private Map<UUID, Integer> waiting;

    public CratesQueue(MaVanity plugin) {
        this.plugin = plugin;
        this.waiting = new HashMap<>();
    }

    public void addCrates(UUID uuid, int crates) {
        if (isWaiting(uuid)) {
            waiting.put(uuid, getCrates(uuid) + crates);
        } else {
            waiting.put(uuid, crates);
        }
    }

    public void removeCrates(UUID uuid) {
        waiting.remove(uuid);
    }

    public boolean isWaiting(UUID uuid) {
        return waiting.containsKey(uuid);
    }

    public int getCrates(UUID uuid) {
        return waiting.get(uuid);
    }
}
