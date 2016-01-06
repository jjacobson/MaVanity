package com.mobarenas.mavanity.utils.managers;

import com.dsh105.echopet.api.EchoPetAPI;
import com.dsh105.echopet.compat.api.entity.IPet;
import com.dsh105.echopet.compat.api.entity.PetType;
import com.dsh105.echopet.compat.api.plugin.EchoPet;
import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.menu.MenuItem;
import com.mobarenas.mavanity.messages.Messages;
import com.mobarenas.mavanity.messages.Pair;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * Created by HP1 on 12/23/2015.
 */
public class PetManager {

    private MaVanity plugin;
    private EchoPetAPI petAPI;

    public PetManager(MaVanity plugin) {
        this.plugin = plugin;
        this.petAPI = EchoPetAPI.getAPI();
    }

    public void petSelect(Player player, MenuItem item) {
        String pet = item.getPetType().toString().toLowerCase().replace("_", " ");
        player.sendMessage(Messages.getMessage("click.pet-select", new Pair("%pet%", pet)));
        if (petAPI.hasPet(player)) {
            petAPI.removePet(player, false, true);
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
        petAPI.givePet(player, item.getPetType(), false);
        plugin.getPlayerManager().getProfile(player.getUniqueId()).setPetsVisible(true);
        for (IPet p : EchoPetAPI.getAPI().getAllPets()) {
            Entity petEntity = p.getEntityPet().getBukkitEntity();
            plugin.getEntityHider().showEntity(player, petEntity);
        }
    }

    public void petName(Player player, MenuItem item) {
        if (!petAPI.hasPet(player)) {
            player.sendMessage(Messages.getMessage("click.no-pet"));
            return;
        }
        if (!player.hasPermission("mobarenas.vip")) {
            player.sendMessage(Messages.getMessage("click.vip-only"));
            return;
        }
        player.setMetadata("pet-name", new FixedMetadataValue(plugin, "true"));
        player.sendMessage(Messages.getMessage("click.name-queue"));
    }

    public void petRemove(Player player, MenuItem item) {
        if (!petAPI.hasPet(player)) {
            player.sendMessage(Messages.getMessage("click.no-pet"));
            return;
        }
        for (ItemStack icon : player.getOpenInventory().getTopInventory()) {
            if (icon == null || icon.getType() == Material.AIR) {
                continue;
            }
            icon.removeEnchantment(Enchantment.DAMAGE_ALL);
        }
        petAPI.removePet(player, false, true);
        player.sendMessage(Messages.getMessage("click.pet-removed"));
    }
}
