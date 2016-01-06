package com.mobarenas.mavanity.menu.menus;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.menu.Menu;
import com.mobarenas.mavanity.menu.MenuPage;
import com.mobarenas.mavanity.menu.PageType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

/**
 * Created by HP1 on 12/22/2015.
 */
public class MainMenu implements Menu {

    private MaVanity plugin;
    private Player player;
    private PageType pageType;
    private List<PageType> pageTypes;

    public MainMenu(MaVanity plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.pageType = PageType.MAIN;
        this.pageTypes = Arrays.asList(PageType.ARMOR, PageType.SETTINGS, PageType.PARTICLE,
                PageType.HAT, PageType.PET, PageType.DISGUISE, PageType.CRATES);
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
        for (PageType type : pageTypes) {
            MenuPage p = plugin.getMenuManager().getPage(type);
            inventory.setItem(p.getSlot(), p.getIcon());
        }
        return inventory;
    }
}
