package com.mobarenas.mavanity.menu.menus;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.menu.*;
import com.mobarenas.mavanity.player.PlayerProfile;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP1 on 12/23/2015.
 */
public class ParticleMenu implements Menu {

    private MaVanity plugin;
    private Player player;
    private PageType pageType;
    private int page;

    public ParticleMenu(MaVanity plugin, Player player, int page) {
        this.plugin = plugin;
        this.player = player;
        this.pageType = PageType.PARTICLE;
        this.page = page;
        open();
    }

    @Override
    public void open() {
        Inventory inventory = createInventory();
        player.openInventory(inventory);
    }

    @Override
    public Inventory createInventory() {
        MenuPage mp = plugin.getMenuManager().getPage(pageType);
        int size = page == 1 ? 0 : 9;
        Inventory inventory = plugin.getServer().createInventory(null, mp.getSize() + size, mp.getName());
        Map<Integer, ItemStack> contents;
        if (page == 1) {
            contents = getEffectInventory();
        } else {
            contents = getParticleInventory();
        }
        for (int slot : contents.keySet()) {
            ItemStack item = contents.get(slot);
            inventory.setItem(slot, item);
        }
        return inventory;
    }

    // actual effects
    private Map<Integer, ItemStack> getEffectInventory() {
        Map<Integer, ItemStack> contents = new HashMap<>();
        contents.put(40, plugin.getMenuManager().getButton(ItemType.BACK).getIcon());
        contents.put(38, plugin.getMenuManager().getButton(ItemType.REMOVE_PARTICLE).getIcon());
        int i = 0;
        for (MenuItem item : plugin.getMenuManager().getLoader().getItems()) {
            if (item.getType() != ItemType.EFFECT) {
                continue;
            }
            if (!player.hasPermission(item.getPermission())) {
                continue;
            }
            if (!player.hasPermission("mobarenas.vip") && item.isVip()) {
                continue;
            }
            ItemStack icon = item.getIcon().clone();
            plugin.getSettingsManager().removeDisabledDisguise(player);
            contents.put(i++, icon);
        }
        return contents;
    }

    // colors and such
    private Map<Integer, ItemStack> getParticleInventory() {
        Map<Integer, ItemStack> contents = new HashMap<>();
        PlayerProfile profile = plugin.getPlayerManager().getProfile(player.getUniqueId());
        contents.put(49, plugin.getMenuManager().getButton(ItemType.PAGE_BACKWARD).getIcon());
        int i = 0;
        for (MenuItem item : plugin.getMenuManager().getLoader().getItems()) {
            if (item.getType() != ItemType.PARTICLE) {
                continue;
            }
            if (!player.hasPermission(item.getPermission())) {
                continue;
            }
            if (!player.hasPermission("mobarenas.vip") && item.isVip()) {
                continue;
            }
            if (!plugin.getAvailableManager().getEffects(profile.getEffectType()).contains(item.getEffect())) {
                continue;
            }
            ItemStack icon = item.getIcon().clone();
            contents.put(i++, icon);
        }
        return contents;
    }
}
