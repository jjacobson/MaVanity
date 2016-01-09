package com.mobarenas.mavanity.utils.managers;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.utils.effects.EffectType;
import org.bukkit.Effect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HP1 on 1/7/2016.
 */
public class AvailableEffectHelper {

    private MaVanity plugin;
    private Map<EffectType, List<Effect>> particleMap;

    public AvailableEffectHelper(MaVanity plugin) {
        this.plugin = plugin;
        this.particleMap = new HashMap<>();
    }

    public void addEffect(EffectType particleEffect, List<Effect> effects) {
        if (particleMap.get(particleEffect) == null) {
            particleMap.put(particleEffect, effects);
        } else {
            particleMap.get(particleEffect).addAll(effects);
        }
    }

    public List<Effect> getEffects(EffectType effect) {
        return particleMap.get(effect);
    }
}
