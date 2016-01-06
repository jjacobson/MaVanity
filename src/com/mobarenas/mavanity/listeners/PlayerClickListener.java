package com.mobarenas.mavanity.listeners;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.menu.ItemType;
import com.mobarenas.mavanity.menu.MenuItem;
import com.mobarenas.mavanity.menu.MenuPage;
import com.mobarenas.mavanity.menu.PageType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by HP1 on 12/23/2015.
 */
public class PlayerClickListener implements Listener {

    private MaVanity plugin;

    public PlayerClickListener(MaVanity plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType() == Material.AIR) {
            return;
        }
        String inventoryName = event.getClickedInventory().getName();
        MenuPage page = plugin.getMenuManager().getPage(inventoryName);
        if (page == null) {
            return;
        }
        event.setCancelled(true);
        ItemStack backButton = plugin.getMenuManager().getButton(ItemType.BACK).getIcon();
        if (backButton.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
            plugin.getMenuManager().openPage(PageType.MAIN, player);
            return;
        }
        switch (page.getType()) {
            case ARMOR:
                armorClick(event);
                break;
            case DISGUISE:
                disguiseClick(event);
                break;
            case HAT:
                hatClick(event);
                break;
            case MAIN:
                mainClick(event);
                break;
            case PARTICLE:
                particleClick(event);
                break;
            case PET:
                petClick(event);
                break;
            case SETTINGS:
                settingsClick(event);
                break;
            case CRATES:
                cratesClick(event);
                break;
        }
    }

    private void armorClick(InventoryClickEvent event) {
        ItemStack icon = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        String itemName = icon.getItemMeta().getDisplayName();
        MenuItem item = plugin.getMenuManager().getButton(itemName);
        switch (item.getType()) {
            case REMOVE_HELMET:
                plugin.getArmorManager().removeHelmet(player);
                break;
            case REMOVE_CHEST:
                plugin.getArmorManager().removeChest(player);
                break;
            case REMOVE_LEGS:
                plugin.getArmorManager().removeLegs(player);
                break;
            case REMOVE_BOOTS:
                plugin.getArmorManager().removeBoots(player);
                break;
            case ARMOR:
                plugin.getArmorManager().armorSelect(player, item);
                break;
            case PAGE_FORWARD:
                plugin.getArmorManager().pageForward(player, item);
                break;
            case PAGE_BACKWARD:
                plugin.getArmorManager().pageBackward(player, item);
                break;
        }
    }

    private void disguiseClick(InventoryClickEvent event) {
        ItemStack icon = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        String itemName = icon.getItemMeta().getDisplayName();
        MenuItem item = plugin.getMenuManager().getButton(itemName);
        switch (item.getType()) {
            case DISGUISE:
                plugin.getDisguiseManager().disguiseSelect(player, item);
                break;
            case VIEWMODE:
                plugin.getDisguiseManager().disguiseMode(player, item);
                break;
            case REMOVE_DISGUISE:
                plugin.getDisguiseManager().disguiseRemove(player, item);
                break;
        }
    }

    private void hatClick(InventoryClickEvent event) {
        ItemStack icon = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        String itemName = icon.getItemMeta().getDisplayName();
        MenuItem item = plugin.getMenuManager().getButton(itemName);
        switch (item.getType()) {
            case HAT:
                plugin.getHatManager().hatSelect(player, item);
                break;
            case REMOVE_HAT:
                plugin.getHatManager().removeHat(player, item);
                break;
        }
    }

    private void mainClick(InventoryClickEvent event) {
        ItemStack icon = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        String pageName = ChatColor.stripColor(icon.getItemMeta().getDisplayName());
        MenuPage page = plugin.getMenuManager().getPage(pageName);
        plugin.getMenuManager().openPage(page.getType(), player);
    }

    private void particleClick(InventoryClickEvent event) {
        ItemStack icon = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        String itemName = icon.getItemMeta().getDisplayName();
        MenuItem item = plugin.getMenuManager().getButton(itemName);
        switch (item.getType()) {
            case PAGE_BACKWARD:
                plugin.getParticleManager().mainPage(player, item);
                break;
            case PARTICLE_PAGE:
                plugin.getParticleManager().particlePage(player, item);
                break;
            case EFFECT_PAGE:
                plugin.getParticleManager().effectPage(player, item);
                break;
            case EFFECT:
                plugin.getParticleManager().effectSelect(player, item);
                break;
            case PARTICLE:
                plugin.getParticleManager().particleSelect(player, item);
                break;
            case REMOVE_PARTICLE:
                plugin.getParticleManager().removeEffect(player, item);
                break;
        }
    }

    private void petClick(InventoryClickEvent event) {
        ItemStack icon = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        String itemName = icon.getItemMeta().getDisplayName();
        MenuItem item = plugin.getMenuManager().getButton(itemName);
        switch (item.getType()) {
            case PET:
                plugin.getPetManager().petSelect(player, item);
                break;
            case NAME:
                plugin.getPetManager().petName(player, item);
                break;
            case REMOVE_PET:
                plugin.getPetManager().petRemove(player, item);
                break;
        }
    }

    private void settingsClick(InventoryClickEvent event) {
        ItemStack icon = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        String itemName = icon.getItemMeta().getDisplayName();
        MenuItem item = plugin.getMenuManager().getButton(itemName);
        switch (item.getType()) {
            case TOGGLE_PLAYERS:
                plugin.getSettingsManager().togglePlayers(player, item);
                break;
            case TOGGLE_PETS:
                plugin.getSettingsManager().togglePets(player, item);
                break;
            case TOGGLE_PARTICLES:
                plugin.getSettingsManager().toggleParticles(player, item);
                break;
            case TOGGLE_DISGUISES:
                plugin.getSettingsManager().toggleDisguises(player, item);
                break;
        }
    }

    private void cratesClick(InventoryClickEvent event) {
        event.getWhoClicked().closeInventory();
        plugin.getCrateManager().openCrate((Player) event.getWhoClicked());
    }
}
