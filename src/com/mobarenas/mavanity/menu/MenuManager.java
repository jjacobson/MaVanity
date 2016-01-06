package com.mobarenas.mavanity.menu;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.menu.menus.*;
import com.mobarenas.mavanity.utils.ParticlePage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by HP1 on 12/22/2015.
 */
public class MenuManager {

    private MaVanity plugin;
    private MenuLoader loader;

    public MenuManager(MaVanity plugin) {
        this.plugin = plugin;
        this.loader = new MenuLoader(plugin);
    }

    /**
     * Get a button object
     *
     * @param name of button
     * @return the button object
     */
    public MenuItem getButton(String name) {
        for (MenuItem item : loader.getItems()) {
            ItemStack icon = item.getIcon();
            if (name.equals(icon.getItemMeta().getDisplayName())) {
                return item;
            }
        }
        return null;
    }

    /**
     * Get a button object by type
     *
     * @param type of button
     * @return the button object
     */
    public MenuItem getButton(ItemType type) {
        for (MenuItem item : loader.getItems()) {
            if (type == item.getType()) {
                return item;
            }
        }
        return null;
    }

    public MenuPage getPage(String name) {
        for (MenuPage page : loader.getPages()) {
            if (page.getName().equals(name)) {
                return page;
            }
        }
        return null;
    }

    public MenuPage getPage(PageType type) {
        for (MenuPage page : loader.getPages()) {
            if (page.getType() == type) {
                return page;
            }
        }
        return null;
    }

    public void openPage(PageType page, Player player) {
        switch (page) {
            case MAIN:
                new MainMenu(plugin, player);
                break;
            case ARMOR:
                new ArmorMenu(plugin, player, 1);
                break;
            case DISGUISE:
                new DisguiseMenu(plugin, player);
                break;
            case HAT:
                new HatMenu(plugin, player);
                break;
            case PARTICLE:
                new ParticleMenu(plugin, player, ParticlePage.MAIN);
                break;
            case PET:
                new PetMenu(plugin, player);
                break;
            case SETTINGS:
                new SettingsMenu(plugin, player);
                break;
            case CRATES:
                new CratesMenu(plugin, player);
                break;
        }
    }

    public MenuLoader getLoader() {
        return loader;
    }
}
