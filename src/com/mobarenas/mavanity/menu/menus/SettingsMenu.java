package com.mobarenas.mavanity.menu.menus;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.menu.ItemType;
import com.mobarenas.mavanity.menu.Menu;
import com.mobarenas.mavanity.menu.MenuPage;
import com.mobarenas.mavanity.menu.PageType;
import com.mobarenas.mavanity.player.PlayerProfile;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by HP1 on 12/23/2015.
 */
public class SettingsMenu implements Menu {

    private MaVanity plugin;
    private Player player;
    private PageType pageType;

    public SettingsMenu(MaVanity plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.pageType = PageType.SETTINGS;
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
        PlayerProfile profile = plugin.getPlayerManager().getProfile(player.getUniqueId());
        Inventory inventory = plugin.getServer().createInventory(null, mp.getSize(), mp.getName());
        ItemStack pets = updateStack(plugin.getMenuManager().getButton(ItemType.TOGGLE_PETS).getIcon(), profile.isPetsVisible());
        ItemStack players = updateStack(plugin.getMenuManager().getButton(ItemType.TOGGLE_PLAYERS).getIcon(), profile.isPlayersVisible());
        ItemStack disguises = updateStack(plugin.getMenuManager().getButton(ItemType.TOGGLE_DISGUISES).getIcon(), profile.isDisguisesVisible());
        ItemStack particles = updateStack(plugin.getMenuManager().getButton(ItemType.TOGGLE_PARTICLES).getIcon(), profile.isParticlesVisible());
        inventory.setItem(31, plugin.getMenuManager().getButton(ItemType.BACK).getIcon());
        inventory.setItem(10, pets);
        inventory.setItem(12, players);
        inventory.setItem(14, disguises);
        inventory.setItem(16, particles);
        return inventory;
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
