package com.mobarenas.mavanity.menu.menus;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.menu.*;
import com.mobarenas.mavanity.player.PlayerProfile;
import com.mobarenas.mavanity.utils.ParticlePage;
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
    private ParticlePage particlePage;

    public ParticleMenu(MaVanity plugin, Player player, ParticlePage particlePage) {
        this.plugin = plugin;
        this.player = player;
        this.pageType = PageType.PARTICLE;
        this.particlePage = particlePage;
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
        int extra = particlePage == ParticlePage.PARTICLE ? 9 : 0;
        Inventory inventory = plugin.getServer().createInventory(null, mp.getSize() + extra, mp.getName());
        Map<Integer, ItemStack> contents = new HashMap<>();
        switch (particlePage) {
            case MAIN:
                contents = getMainInventory();
                break;
            case PARTICLE:
                contents = getParticleInventory();
                break;
            case EFFECT:
                contents = getEffectInventory();
                break;
        }
        for (int slot : contents.keySet()) {
            ItemStack item = contents.get(slot);
            inventory.setItem(slot, item);
        }
        return inventory;
    }

    private Map<Integer, ItemStack> getMainInventory() {
        Map<Integer, ItemStack> contents = new HashMap<>();
        contents.put(40, plugin.getMenuManager().getButton(ItemType.BACK).getIcon());
        contents.put(38, plugin.getMenuManager().getButton(ItemType.REMOVE_PARTICLE).getIcon());
        contents.put(12, plugin.getMenuManager().getButton(ItemType.PARTICLE_PAGE).getIcon());
        contents.put(14, plugin.getMenuManager().getButton(ItemType.EFFECT_PAGE).getIcon());
        return contents;
    }

    // colors and such
    private Map<Integer, ItemStack> getParticleInventory() {
        Map<Integer, ItemStack> contents = new HashMap<>();
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
            ItemStack icon = item.getIcon().clone();
            contents.put(i++, icon);
        }
        return contents;
    }

    // actual effects
    private Map<Integer, ItemStack> getEffectInventory() {
        Map<Integer, ItemStack> contents = new HashMap<>();
        contents.put(40, plugin.getMenuManager().getButton(ItemType.PAGE_BACKWARD).getIcon());
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
}
