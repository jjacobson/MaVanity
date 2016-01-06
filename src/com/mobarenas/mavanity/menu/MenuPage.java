package com.mobarenas.mavanity.menu;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by HP1 on 12/24/2015.
 */
public class MenuPage {

    private ItemStack icon;
    private PageType type;
    private int size;
    private int slot;
    private String name;
    private List<String> description;

    public MenuPage(ItemStack icon, PageType type, int size, int slot, String name, List<String> description) {
        this.icon = icon;
        this.type = type;
        this.size = size;
        this.slot = slot;
        this.name = name;
        this.description = description;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public PageType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return ChatColor.stripColor(name);
    }

    public List<String> getDescription() {
        return description;
    }

    public int getSlot() {
        return slot;
    }
}
