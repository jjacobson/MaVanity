package com.mobarenas.mavanity.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by HP1 on 12/23/2015.
 */
public class ItemParser {

    public static ItemStack parseItem(String item, String name, List<String> description) {
        String[] split = item.split(":");
        Material material = Material.valueOf(split[0]);
        ItemStack stack = new ItemStack(material);
        if (split.length != 1) {
            short damage = (short) Integer.parseInt(split[1]);
            stack.setDurability(damage);
        }
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(description);
        stack.setItemMeta(meta);
        return stack;
    }
}
