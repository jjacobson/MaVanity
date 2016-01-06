package com.mobarenas.mavanity.listeners;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.messages.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

/**
 * Created by HP1 on 12/24/2015.
 */
public class PlayerListener implements Listener {

    private MaVanity plugin;

    public PlayerListener(MaVanity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        plugin.getPlayerManager().loadProfile(event.getPlayer().getUniqueId(), true);
        for (UUID uuid : plugin.getPlayerManager().getProfiles().keySet()) {
            Player player = plugin.getServer().getPlayer(uuid);
            if (player == null || !player.isOnline()) {
                continue;
            }
            if (!plugin.getPlayerManager().getProfile(uuid).isPlayersVisible()) {
                player.hidePlayer(event.getPlayer());
            }
        }
        ItemStack shopItem = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) shopItem.getItemMeta();
        meta.setOwner(event.getPlayer().getName());
        meta.setDisplayName(Messages.getMessage("icon.name"));
        meta.setLore(Messages.getMessages("icon.description"));
        shopItem.setItemMeta(meta);
        event.getPlayer().getInventory().setItem(1, shopItem);
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        plugin.getPlayerManager().removePlayer(event.getPlayer().getUniqueId(), true);
    }

    @EventHandler
    public void playerKick(PlayerKickEvent event) {
        plugin.getPlayerManager().removePlayer(event.getPlayer().getUniqueId(), true);
    }
}
