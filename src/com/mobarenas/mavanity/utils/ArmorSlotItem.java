package com.mobarenas.mavanity.utils;

/**
 * Created by HP1 on 12/24/2015.
 */
public class ArmorSlotItem {

    private int page;
    private int slot;

    public ArmorSlotItem(int page, int slot) {
        this.page = page;
        this.slot = slot;
    }

    public int getPage() {
        return page;
    }

    public int getSlot() {
        return slot;
    }
}
