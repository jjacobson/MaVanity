package com.mobarenas.mavanity.utils.managers;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.menu.ItemType;
import com.mobarenas.mavanity.menu.MenuItem;
import com.mobarenas.mavanity.menu.menus.ArmorMenu;
import com.mobarenas.mavanity.messages.Messages;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP1 on 12/23/2015.
 */
public class ArmorManager {

    private MaVanity plugin;
    private Map<Color, Integer> colorMap;

    public ArmorManager(MaVanity plugin) {
        this.plugin = plugin;
        this.colorMap = new HashMap<>();
        int row = 1;
        for (MenuItem item : plugin.getMenuManager().getLoader().getItems()) {
            if (item.getType() != ItemType.ARMOR) {
                continue;
            }
            Color color = item.getColor();
            if (!colorMap.containsKey(color)) {
                colorMap.put(color, row);
                row++;
            }
        }
    }

    public void armorSelect(Player player, MenuItem item) {
        ArmorType type = item.getArmorType();
        switch (type) {
            case HELMET:
                player.getInventory().setHelmet(item.getIcon());
                break;
            case CHESTPLATE:
                player.getInventory().setChestplate(item.getIcon());
                break;
            case LEGGINGS:
                player.getInventory().setLeggings(item.getIcon());
                break;
            case BOOTS:
                player.getInventory().setBoots(item.getIcon());
                break;
        }
        player.sendMessage(Messages.getMessage("click.armor-chosen"));
        Enchantment enchantment = Enchantment.PROTECTION_ENVIRONMENTAL;
        for (ItemStack i : player.getOpenInventory().getTopInventory()) {
            if (i == null || i.getType() == Material.AIR) {
                continue;
            }
            if (i.getType() == item.getIcon().getType() && i.getEnchantments().containsKey(enchantment)) {
                i.removeEnchantment(enchantment);
            }
            if (i.getItemMeta().getDisplayName().equals(item.getIcon().getItemMeta().getDisplayName())) {
                ItemMeta meta = i.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                i.setItemMeta(meta);
                i.addUnsafeEnchantment(enchantment, 1);
            }
        }
        player.updateInventory();
    }

    public void removeHelmet(Player player) {
        ItemStack stack = player.getInventory().getHelmet();
        if (stack == null || stack.getType() != Material.LEATHER_HELMET) {
            player.sendMessage(Messages.getMessage("click.no-armor"));
            return;
        }
        player.sendMessage(Messages.getMessage("click.armor-removed"));
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        removeEnchant(player.getOpenInventory().getTopInventory(), Material.LEATHER_HELMET);
    }

    public void removeChest(Player player) {
        ItemStack stack = player.getInventory().getChestplate();
        if (stack == null || stack.getType() != Material.LEATHER_CHESTPLATE) {
            player.sendMessage(Messages.getMessage("click.no-armor"));
            return;
        }
        player.sendMessage(Messages.getMessage("click.armor-removed"));
        player.getInventory().setChestplate(new ItemStack(Material.AIR));
        removeEnchant(player.getOpenInventory().getTopInventory(), Material.LEATHER_CHESTPLATE);
    }

    public void removeLegs(Player player) {
        ItemStack stack = player.getInventory().getLeggings();
        if (stack == null || stack.getType() != Material.LEATHER_LEGGINGS) {
            player.sendMessage(Messages.getMessage("click.no-armor"));
            return;
        }
        player.sendMessage(Messages.getMessage("click.armor-removed"));
        player.getInventory().setLeggings(new ItemStack(Material.AIR));
        removeEnchant(player.getOpenInventory().getTopInventory(), Material.LEATHER_LEGGINGS);
    }

    public void removeBoots(Player player) {
        ItemStack stack = player.getInventory().getBoots();
        if (stack == null || stack.getType() != Material.LEATHER_BOOTS) {
            player.sendMessage(Messages.getMessage("click.no-armor"));
            return;
        }
        player.sendMessage(Messages.getMessage("click.armor-removed"));
        player.getInventory().setBoots(new ItemStack(Material.AIR));
        removeEnchant(player.getOpenInventory().getTopInventory(), Material.LEATHER_BOOTS);
    }

    private void removeEnchant(Inventory inventory, Material material) {
        for (ItemStack item : inventory) {
            if (item == null || item.getType() == Material.AIR) {
                continue;
            }
            if (item.getType() == material) {
                item.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
            }
        }
    }

    public void pageForward(Player player, MenuItem item) {
        new ArmorMenu(plugin, player, 2);
    }

    public void pageBackward(Player player, MenuItem item) {
        new ArmorMenu(plugin, player, 1);
    }

    public ArmorSlotItem getSlotAndPage(Color color, ArmorType type) {
        int slot = getColumn(color);
        int page = getPage(color);
        switch (type) {
            case HELMET:
                slot += 0;
                break;
            case CHESTPLATE:
                slot += 9;
                break;
            case LEGGINGS:
                slot += 18;
                break;
            case BOOTS:
                slot += 27;
                break;
        }
        return new ArmorSlotItem(page, slot);
    }

    public int getColumn(Color color) {
        if (getPage(color) == 1) {
            return colorMap.get(color);
        }
        return colorMap.get(color) - 8;
    }

    public int getPage(Color color) {
        return (colorMap.get(color) > 8 ? 2 : 1);
    }
}
