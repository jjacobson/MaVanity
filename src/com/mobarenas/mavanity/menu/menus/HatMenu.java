package com.mobarenas.mavanity.menu.menus;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.menu.*;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by HP1 on 12/22/2015.
 */
public class HatMenu implements Menu {

    private MaVanity plugin;
    private Player player;
    private PageType pageType;

    public HatMenu(MaVanity plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.pageType = PageType.HAT;
        open();
    }

    @Override
    public void open() {
        Inventory inventory = createInventory();
        player.openInventory(inventory);
    }

    @Override
    public Inventory createInventory() {
        MenuPage page = plugin.getMenuManager().getPage(pageType);
        Inventory inventory = plugin.getServer().createInventory(null, page.getSize(), page.getName());
        inventory.setItem(38, plugin.getMenuManager().getButton(ItemType.REMOVE_HAT).getIcon());
        inventory.setItem(40, plugin.getMenuManager().getButton(ItemType.BACK).getIcon());
        for (MenuItem item : plugin.getMenuManager().getLoader().getItems()) {
            if (item.getType() != ItemType.HAT) {
                continue;
            }
            if (!player.hasPermission(item.getPermission())) {
                continue;
            }
            if (!player.hasPermission("mobarenas.vip") && item.isVip()) {
                continue;
            }
            ItemStack icon = item.getIcon().clone();
            ItemStack head = player.getInventory().getHelmet();
            ItemMeta meta = icon.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            icon.setItemMeta(meta);
            if (head != null && head.getType() == icon.getType()) {
                icon.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            }
            inventory.addItem(icon);
        }
        return inventory;
    }
}
