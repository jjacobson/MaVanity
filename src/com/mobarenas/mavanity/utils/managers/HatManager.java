package com.mobarenas.mavanity.utils.managers;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.menu.MenuItem;
import com.mobarenas.mavanity.messages.Messages;
import com.mobarenas.mavanity.messages.Pair;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by HP1 on 12/23/2015.
 */
public class HatManager {

    private MaVanity plugin;

    public HatManager(MaVanity plugin) {
        this.plugin = plugin;
    }

    public void hatSelect(Player player, MenuItem item) {
        player.sendMessage(Messages.getMessage("click.hat-selected", new Pair("%material%", item.getHatName())));
        player.getInventory().setHelmet(item.getIcon());
        Enchantment enchantment = Enchantment.PROTECTION_ENVIRONMENTAL;
        for (ItemStack i : player.getOpenInventory().getTopInventory()) {
            if (i == null || i.getType() == Material.AIR) {
                continue;
            }
            if (i.getEnchantments().containsKey(enchantment)) {
                i.removeEnchantment(enchantment);
            }
            if (i.getType() == item.getIcon().getType()) {
                ItemMeta meta = i.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                i.setItemMeta(meta);
                i.addUnsafeEnchantment(enchantment, 1);
            }
        }
        player.updateInventory();
    }

    public void removeHat(Player player, MenuItem item) {
        ItemStack stack = player.getInventory().getHelmet();
        if (stack == null || stack.getType() == Material.LEATHER) {
            player.sendMessage(Messages.getMessage("click.no-hat"));
            return;
        }
        player.sendMessage(Messages.getMessage("click.hat-removed"));
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        Enchantment enchantment = Enchantment.PROTECTION_ENVIRONMENTAL;
        for (ItemStack i : player.getOpenInventory().getTopInventory()) {
            if (i == null || i.getType() == Material.AIR) {
                continue;
            }
            if (i.getEnchantments().containsKey(enchantment)) {
                i.removeEnchantment(enchantment);
            }
        }
    }
}
