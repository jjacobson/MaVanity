package com.mobarenas.mavanity.listeners;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.menu.PageType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by HP1 on 12/24/2015.
 */
public class PlayerInteractListener implements Listener {

    private MaVanity plugin;

    public PlayerInteractListener(MaVanity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        if (!event.hasItem()) {
            return;
        }
        ItemStack item = event.getItem();
        if (item.getType() != Material.SKULL_ITEM) {
            return;
        }
        event.setCancelled(true);
        plugin.getMenuManager().openPage(PageType.MAIN, event.getPlayer());
    }
}
