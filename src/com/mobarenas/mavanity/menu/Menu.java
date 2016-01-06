package com.mobarenas.mavanity.menu;

import org.bukkit.inventory.Inventory;

/**
 * Created by HP1 on 12/22/2015.
 */
public interface Menu {

    void open();

    Inventory createInventory();
}
