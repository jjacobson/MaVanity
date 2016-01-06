package com.mobarenas.mavanity.menu.menus;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.menu.*;
import com.mobarenas.mavanity.player.PlayerProfile;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.TargetedDisguise;
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
public class DisguiseMenu implements Menu {

    private MaVanity plugin;
    private Player player;
    private PageType pageType;

    public DisguiseMenu(MaVanity plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.pageType = PageType.DISGUISE;
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
        inventory.setItem(38, plugin.getMenuManager().getButton(ItemType.REMOVE_DISGUISE).getIcon());
        inventory.setItem(40, plugin.getMenuManager().getButton(ItemType.BACK).getIcon());
        inventory.setItem(42, plugin.getMenuManager().getButton(ItemType.VIEWMODE).getIcon());
        for (MenuItem item : plugin.getMenuManager().getLoader().getItems()) {
            if (item.getType() != ItemType.DISGUISE) {
                continue;
            }
            if (!player.hasPermission(item.getPermission())) {
                continue;
            }
            if (!player.hasPermission("mobarenas.vip") && item.isVip()) {
                continue;
            }
            ItemStack icon = item.getIcon().clone();
            if (DisguiseAPI.isDisguised(player) && DisguiseAPI.getDisguise(player).getType() == item.getDisguiseType()) {
                ItemMeta meta = icon.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                icon.setItemMeta(meta);
                icon.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
            }
            inventory.addItem(icon);
        }
        return inventory;
    }
}
