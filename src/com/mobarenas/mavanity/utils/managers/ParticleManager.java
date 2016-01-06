package com.mobarenas.mavanity.utils.managers;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.menu.MenuItem;
import com.mobarenas.mavanity.menu.menus.ParticleMenu;
import com.mobarenas.mavanity.messages.Messages;
import com.mobarenas.mavanity.messages.Pair;
import com.mobarenas.mavanity.player.PlayerProfile;
import com.mobarenas.mavanity.utils.ParticlePage;
import com.mobarenas.mavanity.utils.effects.*;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by HP1 on 12/23/2015.
 */
public class ParticleManager {

    private MaVanity plugin;
    private Map<UUID, ParticleEffect> running;

    public ParticleManager(MaVanity plugin) {
        this.plugin = plugin;
        this.running = new HashMap<>();
    }

    public Map<UUID, ParticleEffect> getRunning() {
        return running;
    }

    public void removeEffect(Player player, MenuItem item) {
        if (!running.containsKey(player.getUniqueId())) {
            player.sendMessage(Messages.getMessage("click.no-effect"));
            return;
        }
        player.sendMessage(Messages.getMessage("click.effect-removed"));
        remove(player);
    }

    private void remove(Player player) {
        running.get(player.getUniqueId()).stop();
        running.remove(player.getUniqueId());
        PlayerProfile profile = plugin.getPlayerManager().getProfile(player.getUniqueId());
        profile.setEffectType(null);
    }

    // effect type select (selected a halo etc)
    public void effectSelect(Player player, MenuItem item) {
        EffectType effectType = item.getEffectType();
        PlayerProfile profile = plugin.getPlayerManager().getProfile(player.getUniqueId());
        Effect effect = profile.getEffect();
        if (running.containsKey(player.getUniqueId())) {
            remove(player);
        }
        if (effect == null) {
            profile.setEffect(Effect.HAPPY_VILLAGER); // Default effect
        }
        player.sendMessage(Messages.getMessage("click.effect-selected", new Pair("%effect%", effectType.toString().toLowerCase())));
        profile.setParticlesVisible(true);
        plugin.getSettingsManager().removeDisabledParticles(player);
        profile.setEffectType(effectType);
        start(player);
    }

    public void start(Player player) {
        PlayerProfile profile = plugin.getPlayerManager().getProfile(player.getUniqueId());
        if (profile.getEffectType() == null) {
            return; // no effect selected
        }
        switch (profile.getEffectType()) {
            case HALO:
                running.put(player.getUniqueId(), new HaloEffect(plugin, player));
                break;
            case SPHERE:
                running.put(player.getUniqueId(), new SphereEffect(plugin, player));
                break;
            case WINGS:
                running.put(player.getUniqueId(), new WingsEffect(plugin, player));
                break;
            case RINGS:
                running.put(player.getUniqueId(), new RingsEffect(plugin, player));
                break;
            case VORTEX:
                running.put(player.getUniqueId(), new VortexEffect(plugin, player));
                break;
            case TWISTER:
                running.put(player.getUniqueId(), new TwisterEffect(plugin, player));
                break;
            case SPIRAL:
                running.put(player.getUniqueId(), new SpiralEffect(plugin, player));
                break;
            case FEET:
                running.put(player.getUniqueId(), new FeetEffect(plugin, player));
                break;
            case BODY:
                running.put(player.getUniqueId(), new BodyEffect(plugin, player));
                break;
        }
    }

    public void particleSelect(Player player, MenuItem item) {
        Effect effect = item.getEffect();
        PlayerProfile profile = plugin.getPlayerManager().getProfile(player.getUniqueId());
        profile.setEffect(effect);
        profile.setColor(item.getColor()); // sets to null if no color (not COLORED_DUST)
        player.sendMessage(Messages.getMessage("click.particle-selected"));
    }

    public void mainPage(Player player, MenuItem item) {
        new ParticleMenu(plugin, player, ParticlePage.MAIN);
    }

    public void particlePage(Player player, MenuItem item) {
        new ParticleMenu(plugin, player, ParticlePage.PARTICLE);
    }

    public void effectPage(Player player, MenuItem item) {
        new ParticleMenu(plugin, player, ParticlePage.EFFECT);
    }
}
