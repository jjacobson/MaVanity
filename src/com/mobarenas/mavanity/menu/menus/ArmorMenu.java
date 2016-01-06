package com.mobarenas.mavanity.menu.menus;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.menu.*;
import com.mobarenas.mavanity.utils.ArmorSlotItem;
import com.mobarenas.mavanity.utils.ArmorType;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by HP1 on 12/23/2015.
 */
public class ArmorMenu implements Menu {

    private MaVanity plugin;
    private int pageNumber;
    private Player player;
    private PageType pageType;

    public ArmorMenu(MaVanity plugin, Player player, int pageNumber) {
        this.plugin = plugin;
        this.player = player;
        this.pageNumber = pageNumber;
        this.pageType = PageType.ARMOR;
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
        Inventory inventory = plugin.getServer().createInventory(null, mp.getSize(), mp.getName());
        inventory.setItem(0, plugin.getMenuManager().getButton(ItemType.REMOVE_HELMET).getIcon());
        inventory.setItem(9, plugin.getMenuManager().getButton(ItemType.REMOVE_CHEST).getIcon());
        inventory.setItem(18, plugin.getMenuManager().getButton(ItemType.REMOVE_LEGS).getIcon());
        inventory.setItem(27, plugin.getMenuManager().getButton(ItemType.REMOVE_BOOTS).getIcon());
        inventory.setItem(40, plugin.getMenuManager().getButton(ItemType.BACK).getIcon());
        if (pageNumber == 1) {
            inventory.setItem(44, plugin.getMenuManager().getButton(ItemType.PAGE_FORWARD).getIcon());
        } else {
            inventory.setItem(36, plugin.getMenuManager().getButton(ItemType.PAGE_BACKWARD).getIcon());
        }
        for (MenuItem item : plugin.getMenuManager().getLoader().getItems()) {
            if (item.getType() != ItemType.ARMOR) {
                continue;
            }
            if (!player.hasPermission(item.getPermission())) {
                continue;
            }
            if (!player.hasPermission("mobarenas.vip") && item.isVip()) {
                continue;
            }
            ItemStack icon = item.getIcon().clone();
            ItemMeta meta = icon.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            icon.setItemMeta(meta);
            for (ItemStack stack : player.getInventory().getArmorContents()) {
                if (stack == null || stack.getType() == Material.AIR) {
                    continue;
                }
                if (stack.getItemMeta().getDisplayName() == null) {
                    continue;
                }
                if (stack.getItemMeta().getDisplayName().equals(icon.getItemMeta().getDisplayName())) {
                    icon.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                }
            }
            Color color = item.getColor();
            ArmorType type = item.getArmorType();
            ArmorSlotItem armorSlotItem = plugin.getArmorManager().getSlotAndPage(color, type);
            int page = armorSlotItem.getPage();
            int slot = armorSlotItem.getSlot();
            if (page == pageNumber) {
                inventory.setItem(slot, icon);
            }
        }
        return inventory;
    }
}
