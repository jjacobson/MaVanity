package com.mobarenas.mavanity.listeners;

import com.dsh105.echopet.api.EchoPetAPI;
import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.messages.Messages;
import com.mobarenas.mavanity.messages.Pair;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by HP1 on 12/24/2015.
 */
public class PlayerChatListener implements Listener {

    private MaVanity plugin;

    public PlayerChatListener(MaVanity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!event.getPlayer().hasMetadata("pet-name")) {
            return;
        }
        if (!EchoPetAPI.getAPI().hasPet(player)) {
            return;
        }
        event.setCancelled(true);
        if (event.getMessage().length() > 32 || event.getMessage().length() < 3) {
            player.sendMessage(Messages.getMessage("click"));
            return;
        }
        String name = ChatColor.translateAlternateColorCodes('&', event.getMessage());
        EchoPetAPI.getAPI().getPet(player).setPetName(name);
        player.removeMetadata("pet-name", plugin);

        player.sendMessage(Messages.getMessage("click.name-changed", new Pair("%name%", name)));
    }
}
