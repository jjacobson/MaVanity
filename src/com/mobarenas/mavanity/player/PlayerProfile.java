package com.mobarenas.mavanity.player;

import com.mobarenas.mavanity.utils.effects.EffectType;
import org.bukkit.Color;
import org.bukkit.Effect;

import java.util.UUID;

/**
 * Created by HP1 on 12/22/2015.
 */
public class PlayerProfile {

    private UUID uuid;
    private EffectType effectType;
    private Effect effect;
    private Color color;
    private int crates;
    private boolean playersVisible;
    private boolean petsVisible;
    private boolean disguisesVisible;
    private boolean particlesVisible;

    public PlayerProfile(UUID uuid) {
        this.uuid = uuid;
        this.playersVisible = true;
        this.petsVisible = true;
        this.disguisesVisible = true;
        this.particlesVisible = true;
    }

    public UUID getUuid() {
        return uuid;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    public void setEffectType(EffectType effectType) {
        this.effectType = effectType;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getCrates() {
        return crates;
    }

    public void setCrates(int crates) {
        this.crates = crates;
    }

    public boolean isPlayersVisible() {
        return playersVisible;
    }

    public void setPlayersVisible(boolean playersVisible) {
        this.playersVisible = playersVisible;
    }

    public boolean isPetsVisible() {
        return petsVisible;
    }

    public void setPetsVisible(boolean petsVisible) {
        this.petsVisible = petsVisible;
    }

    public boolean isDisguisesVisible() {
        return disguisesVisible;
    }

    public void setDisguisesVisible(boolean disguisesVisible) {
        this.disguisesVisible = disguisesVisible;
    }

    public boolean isParticlesVisible() {
        return particlesVisible;
    }

    public void setParticlesVisible(boolean particlesVisible) {
        this.particlesVisible = particlesVisible;
    }
}
