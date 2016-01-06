package com.mobarenas.mavanity.menu;

import com.dsh105.echopet.compat.api.entity.PetType;
import com.mobarenas.mavanity.utils.ArmorType;
import com.mobarenas.mavanity.utils.effects.EffectType;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by HP1 on 12/22/2015.
 */
public class MenuItem {

    private ItemStack icon;
    private String permission;
    private ItemType type;
    private boolean vip;
    // Pets and disguises
    private PetType petType;
    private DisguiseType disguiseType;
    // Armor
    private ArmorType armorType;
    // Hat
    private String hatName;
    // particles and effects
    private Effect effect;
    private EffectType effectType;
    // Armor and colored particles
    private Color color;


    public MenuItem(ItemStack icon, String permission, ItemType type, boolean vip) {
        this.icon = icon;
        this.permission = permission;
        this.type = type;
        this.vip = vip;
    }

    // pets
    public MenuItem(ItemStack icon, String permission, ItemType type, boolean vip, PetType petType) {
        this.icon = icon;
        this.permission = permission;
        this.type = type;
        this.vip = vip;
        // Pets
        this.petType = petType;
    }

    // Disguises
    public MenuItem(ItemStack icon, String permission, ItemType type, boolean vip, DisguiseType disguiseType) {
        this.icon = icon;
        this.permission = permission;
        this.type = type;
        this.vip = vip;
        // disguises
        this.disguiseType = disguiseType;
    }

    // Armor
    public MenuItem(ItemStack icon, String permission, ItemType type, boolean vip, ArmorType armorType, Color color) {
        this.icon = icon;
        this.permission = permission;
        this.type = type;
        this.vip = vip;
        // Armor
        this.armorType = armorType;
        this.color = color;
    }

    // Hat
    public MenuItem(ItemStack icon, String permission, ItemType type, boolean vip, String hatName) {
        this.icon = icon;
        this.permission = permission;
        this.type = type;
        this.vip = vip;
        // Hat
        this.hatName = hatName;
    }

    // Particles
    public MenuItem(ItemStack icon, String permission, ItemType type, boolean vip, Effect effect, Color color) {
        this.icon = icon;
        this.permission = permission;
        this.type = type;
        this.vip = vip;
        // Particles
        this.effect = effect;
        this.color = color;
    }

    // Effects
    public MenuItem(ItemStack icon, String permission, ItemType type, boolean vip, EffectType effectType) {
        this.icon = icon;
        this.permission = permission;
        this.type = type;
        this.vip = vip;
        // Effect
        this.effectType = effectType;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public String getPermission() {
        return permission;
    }

    public ItemType getType() {
        return type;
    }

    public boolean isVip() {
        return vip;
    }

    public PetType getPetType() {
        return petType;
    }

    public DisguiseType getDisguiseType() {
        return disguiseType;
    }

    public ArmorType getArmorType() {
        return armorType;
    }

    public Color getColor() {
        return color;
    }

    public String getHatName() {
        return hatName;
    }

    public Effect getEffect() {
        return effect;
    }

    public EffectType getEffectType() {
        return effectType;
    }
}
