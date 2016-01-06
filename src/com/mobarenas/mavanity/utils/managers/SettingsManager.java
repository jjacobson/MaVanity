package com.mobarenas.mavanity.utils.managers;

import com.dsh105.echopet.api.EchoPetAPI;
import com.dsh105.echopet.compat.api.entity.IPet;
import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.menu.MenuItem;
import com.mobarenas.mavanity.messages.Messages;
import com.mobarenas.mavanity.player.PlayerProfile;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.TargetedDisguise;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by HP1 on 12/24/2015.
 */
public class SettingsManager {

    private MaVanity plugin;
    private List<Player> disabledDisguises;
    private List<Player> disabledParticles;

    public SettingsManager(MaVanity plugin) {
        this.plugin = plugin;
        this.disabledDisguises = new ArrayList<>();
        this.disabledParticles = new ArrayList<>();
    }

    public List<Player> getDisabledDisguises() {
        return disabledDisguises;
    }

    public void addDisabledDisguise(Player player) {
        if (!disabledDisguises.contains(player)) {
            disabledDisguises.add(player);
        }
    }

    public void removeDisabledDisguise(Player player) {
        disabledDisguises.remove(player);
    }

    public List<Player> getDisabledParticles() {
        return disabledParticles;
    }

    public void addDisabledParticles(Player player) {
        if (!disabledParticles.contains(player)) {
            disabledParticles.add(player);
        }
    }

    public void removeDisabledParticles(Player player) {
        disabledParticles.remove(player);
    }

    public void togglePlayers(Player player, MenuItem item) {
        PlayerProfile profile = plugin.getPlayerManager().getProfile(player.getUniqueId());
        if (profile.isPlayersVisible()) {
            profile.setPlayersVisible(false);
            plugin.getServer().getOnlinePlayers().forEach(player::hidePlayer);
            player.sendMessage(Messages.getMessage("click.players-off"));
        } else {
            profile.setPlayersVisible(true);
            plugin.getServer().getOnlinePlayers().forEach(player::showPlayer);
            player.sendMessage(Messages.getMessage("click.players-on"));
        }
        update(player.getOpenInventory().getTopInventory(), item.getIcon(), profile.isPlayersVisible());
    }

    public void togglePets(Player player, MenuItem item) {
        PlayerProfile profile = plugin.getPlayerManager().getProfile(player.getUniqueId());
        if (profile.isPetsVisible()) {
            profile.setPetsVisible(false);
            for (IPet pet : EchoPetAPI.getAPI().getAllPets()) {
                Entity petEntity = pet.getEntityPet().getBukkitEntity();
                plugin.getEntityHider().hideEntity(player, petEntity);
            }
            player.sendMessage(Messages.getMessage("click.pets-off"));
        } else {
            profile.setPetsVisible(true);
            for (IPet pet : EchoPetAPI.getAPI().getAllPets()) {
                Entity petEntity = pet.getEntityPet().getBukkitEntity();
                plugin.getEntityHider().showEntity(player, petEntity);
            }
            player.sendMessage(Messages.getMessage("click.pets-on"));
        }
        update(player.getOpenInventory().getTopInventory(), item.getIcon(), profile.isPetsVisible());
    }

    public void toggleParticles(Player player, MenuItem item) {
        PlayerProfile profile = plugin.getPlayerManager().getProfile(player.getUniqueId());
        if (profile.isParticlesVisible()) {
            profile.setParticlesVisible(false);
            addDisabledParticles(player);
            player.sendMessage(Messages.getMessage("click.particles-off"));
        } else {
            profile.setParticlesVisible(true);
            removeDisabledParticles(player);
            player.sendMessage(Messages.getMessage("click.particles-on"));
        }
        update(player.getOpenInventory().getTopInventory(), item.getIcon(), profile.isParticlesVisible());
    }

    public void toggleDisguises(Player player, MenuItem item) {
        PlayerProfile profile = plugin.getPlayerManager().getProfile(player.getUniqueId());
        if (profile.isDisguisesVisible()) {
            profile.setDisguisesVisible(false);
            addDisabledDisguise(player);
            plugin.getServer().getOnlinePlayers().stream().filter(DisguiseAPI::isDisguised).forEach(p -> {
                ((TargetedDisguise) DisguiseAPI.getDisguise(p)).addPlayer(player);
            });
            player.sendMessage(Messages.getMessage("click.disguises-off"));
        } else {
            profile.setDisguisesVisible(true);
            removeDisabledDisguise(player);
            plugin.getServer().getOnlinePlayers().stream().filter(DisguiseAPI::isDisguised).forEach(p -> {
                ((TargetedDisguise) DisguiseAPI.getDisguise(p)).removePlayer(player);
            });
            player.sendMessage(Messages.getMessage("click.disguises-on"));
        }
        update(player.getOpenInventory().getTopInventory(), item.getIcon(), profile.isDisguisesVisible());
    }

    public void update(Inventory top, ItemStack clicked, boolean value) {
        for (int i = 0; i < top.getSize(); i++) {
            ItemStack item = top.getItem(i);
            if (item == null || item.getType() == Material.AIR) {
                continue;
            }
            if (!clicked.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                continue;
            }
            ItemStack clone = updateStack(clicked, value);
            top.setItem(i, clone);
        }
    }

    private ItemStack updateStack(ItemStack stack, boolean enabled) {
        ItemStack clone = stack.clone();
        ItemMeta meta = clone.getItemMeta();
        List<String> lore = meta.getLore();
        for (int i = 0; i < lore.size(); i++) {
            lore.set(i, lore.get(i).replace("%value%", booleanToString(enabled)));
        }
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        clone.setItemMeta(meta);
        if (enabled) {
            clone.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        }
        return clone;
    }

    private String booleanToString(boolean value) {
        return value ? "visible" : "hidden";
    }
}
