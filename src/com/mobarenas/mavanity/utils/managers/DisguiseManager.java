package com.mobarenas.mavanity.utils.managers;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.menu.MenuItem;
import com.mobarenas.mavanity.messages.Messages;
import com.mobarenas.mavanity.messages.Pair;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.TargetedDisguise;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by HP1 on 12/23/2015.
 */
public class DisguiseManager {

    private MaVanity plugin;

    public DisguiseManager(MaVanity plugin) {
        this.plugin = plugin;
    }

    public void disguiseSelect(Player player, MenuItem item) {
        String disguise = item.getDisguiseType().toString().toLowerCase().replace("_", " ");
        player.sendMessage(Messages.getMessage("click.disguise-selected", new Pair("%disguise%", disguise)));
        if (DisguiseAPI.isDisguised(player)) {
            DisguiseAPI.getDisguise(player).removeDisguise();
            for (ItemStack icon : player.getOpenInventory().getTopInventory()) {
                if (icon == null || icon.getType() == Material.AIR) {
                    continue;
                }
                icon.removeEnchantment(Enchantment.DAMAGE_ALL);
            }
        }
        for (ItemStack icon : player.getOpenInventory().getTopInventory()) {
            if (icon == null || icon.getType() == Material.AIR) {
                continue;
            }
            if (icon.getItemMeta().getDisplayName().equals(item.getIcon().getItemMeta().getDisplayName())) {
                ItemMeta meta = icon.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                icon.setItemMeta(meta);
                icon.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
            }
        }
        DisguiseType disguiseType = item.getDisguiseType();
        DisguiseAPI.disguiseIgnorePlayers(player, new MobDisguise(disguiseType, true), plugin.getSettingsManager().getDisabledDisguises());
        DisguiseAPI.getDisguise(player).setShowName(true);
        plugin.getPlayerManager().getProfile(player.getUniqueId()).setDisguisesVisible(true);
        plugin.getSettingsManager().removeDisabledDisguise(player);
        plugin.getServer().getOnlinePlayers().stream().filter(DisguiseAPI::isDisguised).forEach(p -> {
            ((TargetedDisguise) DisguiseAPI.getDisguise(p)).removePlayer(player);
        });
    }

    public void disguiseMode(Player player, MenuItem item) {
        if (!DisguiseAPI.isDisguised(player)) {
            player.sendMessage(Messages.getMessage("click.no-disguise"));
            return;
        }
        Disguise disguise = DisguiseAPI.getDisguise(player);
        if (disguise.isSelfDisguiseVisible()) {
            disguise.setViewSelfDisguise(false);
            player.sendMessage(Messages.getMessage("click.viewable-false"));
        } else {
            disguise.setViewSelfDisguise(true);
            player.sendMessage(Messages.getMessage("click.viewable-true"));
        }
    }

    public void disguiseRemove(Player player, MenuItem item) {
        if (!DisguiseAPI.isDisguised(player)) {
            player.sendMessage(Messages.getMessage("click.no-disguise"));
            return;
        }
        for (ItemStack icon : player.getOpenInventory().getTopInventory()) {
            if (icon == null || icon.getType() == Material.AIR) {
                continue;
            }
            icon.removeEnchantment(Enchantment.DAMAGE_ALL);
        }
        DisguiseAPI.getDisguise(player).removeDisguise();
        player.sendMessage(Messages.getMessage("click.disguise-removed"));
    }
}
