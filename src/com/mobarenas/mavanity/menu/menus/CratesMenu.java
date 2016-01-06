package com.mobarenas.mavanity.menu.menus;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.menu.*;
import com.mobarenas.mavanity.messages.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by HP1 on 1/5/2016.
 */
public class CratesMenu implements Menu {

    private MaVanity plugin;
    private Player player;
    private PageType pageType;

    public CratesMenu(MaVanity plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.pageType = PageType.CRATES;
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
        inventory.setItem(40, plugin.getMenuManager().getButton(ItemType.BACK).getIcon());
        int i = 0;
        int c = plugin.getPlayerManager().getProfile(player.getUniqueId()).getCrates();
        for (MenuItem menuItem : plugin.getMenuManager().getLoader().getItems()) {
            if (player.hasPermission(menuItem.getPermission())) {
                continue;
            }
            if (menuItem.isVip() && !player.hasPermission("mobarenas.vip")) {
                continue;
            }
            if (!plugin.getCrateManager().getItemTypes().contains(menuItem.getType())) {
                continue;
            }
            if (i == page.getSize() - 9) { // dont overfill the chest
                break;
            }
            if (c == 0) { // dont give them more than they have
                break;
            }
            ItemStack chest = new ItemStack(Material.CHEST);
            ItemMeta meta = chest.getItemMeta();
            meta.setDisplayName(Messages.getMessage("crates.name"));
            meta.setLore(Messages.getMessages("crates.description"));
            chest.setItemMeta(meta);
            inventory.setItem(i++, chest);
            c--;
        }
        return inventory;
    }
}
