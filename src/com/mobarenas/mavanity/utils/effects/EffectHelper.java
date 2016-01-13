package com.mobarenas.mavanity.utils.effects;

import com.mobarenas.mavanity.MaVanity;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 * Created by HP1 on 1/1/2016.
 */
public class EffectHelper {

    private static MaVanity plugin;
    private static Random random;
    private static final Color[] COLORS = {
            Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.MAROON,
            Color.NAVY, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW
    };

    public EffectHelper(MaVanity plugin) {
        this.plugin = plugin;
        this.random = new Random();
    }

    public static void showEffect(Location loc, Effect effect, Color color, int amount) {
        switch (effect) {
            case COLOURED_DUST:
            case POTION_SWIRL:
                showColor(loc, effect, color, amount);
                break;
            case NOTE:
                showNote(loc, effect, color, amount);
                break;
            case FIREWORKS_SPARK:
                showFirework(loc, effect);
                break;
            case VOID_FOG:
                showFog(loc, effect);
                break;
            case CRIT:
            case MAGIC_CRIT:
                showCrit(loc, effect);
                break;
            case WITCH_MAGIC:
                showWitch(loc, effect);
                break;
            case PORTAL:
                showPortal(loc, effect);
                break;
            case FLYING_GLYPH:
                showGlyph(loc, effect);
                break;
            default:
                showEffect(loc, effect, amount);
        }
    }

    private static void showGlyph(Location loc, Effect effect) {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (plugin.getSettingsManager().getDisabledParticles().contains(p)) {
                continue;
            }
            p.spigot().playEffect(loc, effect, 0, 0, 0.03F, 0.03F, 0.03F, 0.0F, 3, 16);
        }
    }

    private static void showFirework(Location loc, Effect effect) {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (plugin.getSettingsManager().getDisabledParticles().contains(p)) {
                continue;
            }
            p.spigot().playEffect(loc, effect, 0, 0, 0.0F, 0.5F, 0.0F, 0.1F, 0, 16);
        }
    }

    private static void showColor(Location loc, Effect effect, Color color, int amount) {
        float r = color.getRed();
        float g = color.getGreen();
        float b = color.getBlue();
        if (r == 1.0 && g == 1.0 && b == 1.0) {
            Color randomColor = COLORS[random.nextInt(COLORS.length)];
            r = randomColor.getRed();
            g = randomColor.getGreen();
            b = randomColor.getBlue();
        }
        if (r == 0) {
            r = 1.175494E-038F;
        }
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (plugin.getSettingsManager().getDisabledParticles().contains(p)) {
                continue;
            }
            for (int i = 0; i < amount; i++) {
                p.spigot().playEffect(loc, effect, 0, 0, r / 255, g / 255, b / 255, 1, 0, 16);
            }
        }
    }

    private static void showNote(Location loc, Effect effect, Color color, int amount) {
        float x = color.getRed() == 0 ? random.nextInt(25) : color.getRed();
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (plugin.getSettingsManager().getDisabledParticles().contains(p)) {
                continue;
            }
            for (int i = 0; i < amount; i++) {
                p.spigot().playEffect(loc, effect, 0, 0, x / 24.0F, 0, 0, 1, 0, 16);
            }
        }
    }

    private static void showWitch(Location loc, Effect effect) {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (plugin.getSettingsManager().getDisabledParticles().contains(p)) {
                continue;
            }
            p.spigot().playEffect(loc, effect, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 1, 16);
        }
    }

    private static void showPortal(Location loc, Effect effect) {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (plugin.getSettingsManager().getDisabledParticles().contains(p)) {
                continue;
            }
            for (int i = 0; i < 5; i++) {
                p.spigot().playEffect(loc, effect, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 1, 16);
            }
        }
    }

    private static void showCrit(Location loc, Effect effect) {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (plugin.getSettingsManager().getDisabledParticles().contains(p)) {
                continue;
            }
            p.spigot().playEffect(loc, effect, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 1, 16);
        }
    }

    private static void showFog(Location loc, Effect effect) {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (plugin.getSettingsManager().getDisabledParticles().contains(p)) {
                continue;
            }
            p.spigot().playEffect(loc, effect, 0, 0, 0.03F, 0.03F, 0.03F, 0.1F, 5, 16);
        }
    }

    private static void showEffect(Location loc, Effect effect, int amount) {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (plugin.getSettingsManager().getDisabledParticles().contains(p)) {
                continue;
            }
            for (int i = 0; i < amount; i++) {
                p.spigot().playEffect(loc, effect, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 1, 16);
            }
        }
    }
}
