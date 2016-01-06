package com.mobarenas.mavanity.listeners;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.events.VanityPlayerLoadEvent;
import com.mobarenas.mavanity.player.PlayerProfile;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.TargetedDisguise;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by HP1 on 12/29/2015.
 */
public class PlayerLoadedListener implements Listener {

    private MaVanity plugin;

    public PlayerLoadedListener(MaVanity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerLoaded(VanityPlayerLoadEvent event) {
        PlayerProfile profile = plugin.getPlayerManager().getProfile(event.getProfile().getUuid());
        if (plugin.getCratesQueue().isWaiting(profile.getUuid())) { // give them any queued crates and unload them
            profile.setCrates(profile.getCrates() + plugin.getCratesQueue().getCrates(profile.getUuid()));
            plugin.getCratesQueue().removeCrates(profile.getUuid());
            plugin.getPlayerManager().removePlayer(profile.getUuid(), true);
            return;
        }
        if (!event.isOnline()) { // return if we are just loading to modify their profile
            return;
        }
        Player player = plugin.getServer().getPlayer(event.getProfile().getUuid());
        if (player == null || !player.isOnline()) { // if they have disconnected during loading, skip this
            return;
        }
        plugin.getParticleManager().start(player); // start their selected particle effect
        if (!profile.isDisguisesVisible()) { // Hide all disguises on the server if they have it disabled
            plugin.getSettingsManager().addDisabledDisguise(player);
            plugin.getServer().getOnlinePlayers().stream().filter(DisguiseAPI::isDisguised).forEach(p -> {
                ((TargetedDisguise) DisguiseAPI.getDisguise(p)).addPlayer(player);
            });
        }
        if (!profile.isPlayersVisible()) {
            plugin.getServer().getOnlinePlayers().forEach(player::hidePlayer);
        }
        if (!profile.isParticlesVisible()) {
            plugin.getSettingsManager().addDisabledParticles(player);
        }
    }
}
