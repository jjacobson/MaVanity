package com.mobarenas.mavanity.listeners;

import com.dsh105.echopet.compat.api.event.PetPreSpawnEvent;
import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.player.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

/**
 * Created by HP1 on 12/31/2015.
 */
public class PetSpawnListener implements Listener {

    private MaVanity plugin;

    public PetSpawnListener(MaVanity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void petSpawn(PetPreSpawnEvent event) {
        String pet = event.getPet().getPetType().toString().toLowerCase();
        event.getPet().getOwner().addAttachment(plugin, "echopet.pet.type." + pet + ".*", true);
        event.getPet().getOwner().addAttachment(plugin, "echopet.pet.type." + pet, true);
        Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
            Entity petEntity = event.getPet().getEntityPet().getBukkitEntity();
            for (UUID uuid : plugin.getPlayerManager().getProfiles().keySet()) {
                Player player = plugin.getServer().getPlayer(uuid);
                if (player == null || !player.isOnline()) {
                    continue;
                }
                PlayerProfile profile = plugin.getPlayerManager().getProfile(uuid);
                if (!profile.isPetsVisible()) {
                    plugin.getEntityHider().hideEntity(player, petEntity);
                }
            }
        }, 10L);
    }
}
