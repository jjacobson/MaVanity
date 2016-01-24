package com.mobarenas.mavanity.rewards;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.menu.ItemType;
import com.mobarenas.mavanity.menu.MenuItem;
import com.mobarenas.mavanity.messages.Messages;
import com.mobarenas.mavanity.messages.Pair;
import com.mobarenas.mavanity.player.PlayerProfile;
import com.mobarenas.mavanity.utils.ItemFountain;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

/**
 * Created by HP1 on 1/5/2016.
 */
public class CrateManager {

    private MaVanity plugin;
    private Random random;
    private Permission permission;
    private static final EnumSet<ItemType> ITEM_TYPES = EnumSet.of(
            ItemType.ARMOR, ItemType.DISGUISE, ItemType.PET, ItemType.EFFECT, ItemType.PARTICLE, ItemType.HAT
    );

    public CrateManager(MaVanity plugin) {
        this.plugin = plugin;
        this.permission = plugin.getServer().getServicesManager().getRegistration(Permission.class).getProvider();
        this.random = new Random();
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "crate-alerts");
    }

    public void openCrate(Player player) {
        List<MenuItem> unownedCrates = new ArrayList<>();
        PlayerProfile profile = plugin.getPlayerManager().getProfile(player.getUniqueId());
        for (MenuItem item : plugin.getMenuManager().getLoader().getItems()) {
            if (player.hasPermission(item.getPermission())) {
                continue;
            }
            if (item.isVip() && !player.hasPermission("mobarenas.vip")) {
                continue;
            }
            if (!getItemTypes().contains(item.getType())) {
                continue;
            }
            unownedCrates.add(item);
        }
        MenuItem randomItem = unownedCrates.get(random.nextInt(unownedCrates.size()));
        permission.playerAdd(player, randomItem.getPermission());
        profile.setCrates(profile.getCrates() - 1);
        new ItemFountain(plugin, player, randomItem.getIcon());
        sendMessage(player, randomItem);
    }

    public EnumSet<ItemType> getItemTypes() {
        return ITEM_TYPES;
    }

    private void sendMessage(Player player, MenuItem menuItem) {
        String type = menuItem.getType().toString().toLowerCase();
        String caps = Character.toString(type.charAt(0)).toUpperCase();
        String name = caps + type.substring(1, type.length());
        String iconName = ChatColor.stripColor(menuItem.getIcon().getItemMeta().getDisplayName().toLowerCase());
        String title = Messages.getMessage("crates.title", new Pair("%type%", name));
        String subtitle = Messages.getMessage("crates.subtitle", new Pair("%name%", iconName));
        String request = title + ":" + subtitle + ":" + player.getUniqueId().toString();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (player == null || !player.isOnline()) {
                return;
            }
            player.sendPluginMessage(plugin, "crate-alerts", stream.toByteArray());
        }, 60L);
    }
}
