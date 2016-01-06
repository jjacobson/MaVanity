package com.mobarenas.mavanity.utils;

import com.mobarenas.mavanity.MaVanity;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

/**
 * Created by HP1 on 1/6/2016.
 */
public class ItemFountain {

    private MaVanity plugin;
    private Player player;
    private ItemStack itemStack;
    private int i = 0;

    public ItemFountain(MaVanity plugin, Player player, ItemStack itemStack) {
        this.plugin = plugin;
        this.player = player;
        this.itemStack = itemStack;
        start();
    }

    private void start() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if (i == 20) {
                return;
            }
            Location spawnLoc = player.getLocation().add(0, 2.0, 0);
            Item item = spawnLoc.getWorld().dropItemNaturally(spawnLoc, getNextItem());
            item.setPickupDelay(Integer.MAX_VALUE);
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> item.remove(), 60L);
            Firework firework = (Firework) spawnLoc.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> firework.remove(), 1L);
            i++;
        }, 0L, 5L);
    }

    private ItemStack getNextItem() {
        ItemStack next = itemStack.clone();
        ItemMeta meta = next.getItemMeta();
        meta.setDisplayName(UUID.randomUUID().toString());
        next.setItemMeta(meta);
        return next;
    }
}
