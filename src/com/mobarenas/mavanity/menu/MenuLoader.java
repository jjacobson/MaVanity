package com.mobarenas.mavanity.menu;

import com.dsh105.echopet.compat.api.entity.PetType;
import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.messages.Messages;
import com.mobarenas.mavanity.messages.Pair;
import com.mobarenas.mavanity.utils.ArmorType;
import com.mobarenas.mavanity.utils.ItemParser;
import com.mobarenas.mavanity.utils.effects.EffectType;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by HP1 on 12/22/2015.
 */
public class MenuLoader {

    private MaVanity plugin;
    private List<MenuItem> items;
    private List<MenuPage> pages;

    public MenuLoader(MaVanity plugin) {
        this.plugin = plugin;
        this.items = new ArrayList<>();
        this.pages = new ArrayList<>();
        loadPages();
        loadStaticItems();
        loadDisguises();
        loadPets();
        loadArmor();
        loadHats();
        loadParticles();
        loadEffects();
    }

    // TODO: 1/6/2016 cleanup this class
    private void loadPages() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("pages");
        for (String p : section.getKeys(false)) {
            ConfigurationSection page = section.getConfigurationSection(p);
            String pageName = Messages.getMessage(page.getCurrentPath() + ".name");
            Material material = Material.valueOf(page.getString("material"));
            short damage = 0;
            if (page.getString("damage") != null) {
                damage = (short) page.getInt("damage");
            }
            List<String> description = Messages.getMessages(page.getCurrentPath() + ".description");
            int size = page.getInt("size");
            int slot = page.getInt("slot");
            PageType type = PageType.valueOf(page.getString("type"));
            ItemStack stack = new ItemStack(material, 1, damage);
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(pageName);
            meta.setLore(description);
            stack.setItemMeta(meta);
            pages.add(new MenuPage(stack, type, size, slot, pageName, description));
        }
    }

    private void loadHats() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("hats");
        for (String hat : section.getKeys(false)) {
            ConfigurationSection sec = section.getConfigurationSection(hat);
            String[] split = hat.split("-");
            Material material = Material.valueOf(split[0]);
            ItemStack stack = new ItemStack(material);
            ItemMeta meta = stack.getItemMeta();
            String title;
            String permission = "mobarenas.vanity.hat." + split[0].toLowerCase();
            if (material != Material.BANNER) {
                stack.setDurability((short) sec.getInt("damage"));
                String mat = material.toString().toLowerCase().replace("_", " ");
                String caps = Character.toString(mat.charAt(0)).toUpperCase();
                title = caps + mat.substring(1, mat.length());
            } else {
                String bannerType = split[1].toLowerCase();
                String caps = Character.toString(bannerType.charAt(0)).toUpperCase();
                title = caps + bannerType.substring(1, bannerType.length()) + " " + split[0].toLowerCase().replace("_", " ");
                for (String pattern : sec.getStringList("patterns")) {
                    String[] patternSplit = pattern.split(":");
                    PatternType type = PatternType.valueOf(patternSplit[0]);
                    ((BannerMeta) meta).addPattern(new Pattern(DyeColor.valueOf(patternSplit[1]), type));
                }
                permission += ("." + bannerType.toLowerCase());
            }
            meta.setDisplayName(Messages.getMessage("hats.name", new Pair("%material%", title)));
            meta.setLore(Messages.getMessages("hats.description", new Pair("%material%", title.toLowerCase())));
            stack.setItemMeta(meta);
            items.add(new MenuItem(stack, permission, ItemType.HAT, sec.getBoolean("vip"), title.toLowerCase()));
        }
    }

    private void loadParticles() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("particles.particles");
        for (String particle : section.getKeys(false)) {
            ConfigurationSection sec = section.getConfigurationSection(particle);
            Effect effect = Effect.valueOf(particle.split("-")[0]);
            Material material = Material.valueOf(sec.getString("item"));
            short damage = 0;
            if (sec.getString("damage") != null) {
                damage = (short) sec.getInt("damage");
            }
            boolean vip = sec.getBoolean("vip");
            String permission = sec.getString("permission");
            ItemStack stack = new ItemStack(material, 1, damage);
            ItemMeta meta = stack.getItemMeta();
            String name = Messages.getMessage(sec.getCurrentPath() + ".name");
            List<String> description = Messages.getMessages(sec.getCurrentPath() + ".description");
            meta.setDisplayName(name);
            meta.setLore(description);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            stack.setItemMeta(meta);
            Color color = null;
            if (effect == Effect.COLOURED_DUST || effect == Effect.NOTE || effect == Effect.POTION_SWIRL) {
                String[] split = sec.getString("color").split(":");
                color = Color.fromRGB(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
            }
            items.add(new MenuItem(stack, permission, ItemType.PARTICLE, vip, effect, color));
        }
    }

    private void loadEffects() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("particles.effects");
        for (String effect : section.getKeys(false)) {
            ConfigurationSection sec = section.getConfigurationSection(effect);
            EffectType effectType = EffectType.valueOf(effect);
            Material material = Material.valueOf(sec.getString("item"));
            short damage = 0;
            if (sec.getString("damage") != null) {
                damage = (short) sec.getInt("damage");
            }
            boolean vip = sec.getBoolean("vip");
            String permission = "mobarenas.vanity.effects." + effect.toLowerCase();
            ItemStack stack = new ItemStack(material, 1, damage);
            ItemMeta meta = stack.getItemMeta();
            String m = effect.toLowerCase();
            String caps = Character.toString(m.charAt(0)).toUpperCase();
            String title = caps + m.substring(1, m.length());
            String name = Messages.getMessage(section.getCurrentPath() + ".name", new Pair("%effect%", title));
            List<String> description = Messages.getMessages(section.getCurrentPath() + ".description", new Pair("%effect%", effect.toLowerCase()));
            meta.setDisplayName(name);
            meta.setLore(description);
            stack.setItemMeta(meta);
            List<Effect> effects = sec.getStringList("effects").stream().map(Effect::valueOf).collect(Collectors.toList());
            plugin.getAvailableManager().addEffect(effectType, effects);
            items.add(new MenuItem(stack, permission, ItemType.EFFECT, vip, effectType));
        }
    }

    private void loadDisguises() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("disguises");
        for (String entry : section.getKeys(false)) {
            ConfigurationSection sec = section.getConfigurationSection(entry);
            DisguiseType disguiseType = DisguiseType.valueOf(entry);
            Material material = Material.valueOf(sec.getString("item"));
            short damage = (short) sec.getInt("damage");
            ItemStack stack = new ItemStack(material, 1, damage);
            ItemMeta meta = stack.getItemMeta();
            String m = entry.toLowerCase().replace("_", " ");
            String caps = Character.toString(m.charAt(0)).toUpperCase();
            String mob = caps + m.substring(1, m.length());
            meta.setDisplayName(Messages.getMessage("disguise.name", new Pair("%type%", mob)));
            meta.setLore(Messages.getMessages("disguise.description", new Pair("%type%", m)));
            stack.setItemMeta(meta);
            boolean vip = sec.getBoolean("vip");
            String permission = "mobarenas.vanity.disguise." + entry.toString().toLowerCase();
            items.add(new MenuItem(stack, permission, ItemType.DISGUISE, vip, disguiseType));
        }
    }

    private void loadPets() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("pets");
        for (String entry : section.getKeys(false)) {
            ConfigurationSection sec = section.getConfigurationSection(entry);
            PetType petType = PetType.valueOf(entry);
            Material material = Material.valueOf(sec.getString("item"));
            short damage = (short) sec.getInt("damage");
            ItemStack stack = new ItemStack(material, 1, damage);
            ItemMeta meta = stack.getItemMeta();
            String m = entry.toLowerCase().replace("_", " ");
            String caps = Character.toString(m.charAt(0)).toUpperCase();
            String mob = caps + m.substring(1, m.length());
            meta.setDisplayName(Messages.getMessage("pet.name", new Pair("%type%", mob)));
            meta.setLore(Messages.getMessages("pet.description", new Pair("%type%", m)));
            stack.setItemMeta(meta);
            boolean vip = sec.getBoolean("vip");
            String permission = "mobarenas.vanity.pet." + entry.toString().toLowerCase();
            items.add(new MenuItem(stack, permission, ItemType.PET, vip, petType));
        }
    }

    private void loadArmor() {
        for (String string : plugin.getConfig().getStringList("armor.colors")) {
            String[] sp = string.split(":");
            String name = sp[3];
            Color color = Color.fromRGB(Integer.parseInt(sp[0]), Integer.parseInt(sp[1]), Integer.parseInt(sp[2]));
            loadArmorPiece(ArmorType.HELMET, color, name);
            loadArmorPiece(ArmorType.CHESTPLATE, color, name);
            loadArmorPiece(ArmorType.LEGGINGS, color, name);
            loadArmorPiece(ArmorType.BOOTS, color, name);
        }
    }

    private void loadArmorPiece(ArmorType type, Color color, String colorName) {
        Material material = null;
        switch (type) {
            case HELMET:
                material = Material.LEATHER_HELMET;
                break;
            case CHESTPLATE:
                material = Material.LEATHER_CHESTPLATE;
                break;
            case LEGGINGS:
                material = Material.LEATHER_LEGGINGS;
                break;
            case BOOTS:
                material = Material.LEATHER_BOOTS;
                break;
        }
        ItemStack stack = new ItemStack(material);
        LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
        meta.setColor(color);
        String piece = type.toString().toLowerCase();
        meta.setDisplayName(Messages.getMessage("armor.name", new Pair("%color%", colorName), new Pair("%piece%", piece)));
        meta.setLore(Messages.getMessages("armor.description"));
        stack.setItemMeta(meta);
        ItemType itemType = ItemType.ARMOR;
        String permission = ("mobarenas.vanity." + type.toString().toLowerCase() + "." + colorName);
        items.add(new MenuItem(stack, permission, itemType, false, type, color));
    }

    private void loadStaticItems() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("static-buttons");
        for (String staticItem : section.getKeys(false)) {
            ConfigurationSection item = section.getConfigurationSection(staticItem);
            String itemType = item.getString("item");
            ItemType type = ItemType.valueOf(item.getString("type"));
            String name = Messages.getMessage(item.getCurrentPath() + ".name");
            String permission = item.getString("permission");
            boolean vip = item.getBoolean("vip");
            List<String> description = Messages.getMessages(item.getCurrentPath() + ".description");
            ItemStack stack = ItemParser.parseItem(itemType, name, description);
            items.add(new MenuItem(stack, permission, type, vip));
        }
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public List<MenuPage> getPages() {
        return pages;
    }
}
