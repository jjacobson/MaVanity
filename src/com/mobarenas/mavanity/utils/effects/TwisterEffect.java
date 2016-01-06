package com.mobarenas.mavanity.utils.effects;

import com.mobarenas.mavanity.MaVanity;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by HP1 on 1/1/2016.
 */
public class TwisterEffect extends BukkitRunnable implements ParticleEffect {

    private MaVanity plugin;
    private Player player;
    private boolean isAnimating;

    private final double yOffset = 0.15D;
    private final float tornadoHeight = 3.15F;
    private final float maxTornadoRadius = 2.25F;
    private final double distance = 0.375D;

    public TwisterEffect(MaVanity plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.runTaskTimer(plugin, 0L, 5L);
    }

    @Override
    public void run() {
        if (player == null || !player.isOnline()) {
            this.stop();
            return;
        }
        Effect effect = plugin.getPlayerManager().getProfile(player.getUniqueId()).getEffect();
        Color color = plugin.getPlayerManager().getProfile(player.getUniqueId()).getColor();
        Location loc = player.getLocation();
        if (isAnimating) {
            return;
        }
        isAnimating = true;
        int step = 0;
        final Location var20 = loc.clone().add(0.0D, 0.2D, 0.0D);
        double var21 = 0.25D * (double) maxTornadoRadius * (2.35D / (double) tornadoHeight);
        double var10 = 0.0D;
        double var12 = 0.08;
        for (double var14 = (double) this.tornadoHeight; var14 > 0.0D; var14 -= distance) {
            double var16 = var21 * var14;
            if (var16 > (double) this.maxTornadoRadius) {
                var16 = (double) this.maxTornadoRadius;
            }
            Iterator var19 = this.createCircle(var14, var16).iterator();
            while (var19.hasNext()) {
                final Vector var18 = (Vector) var19.next();
                var10 += var12;
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                    EffectHelper.showEffect(var20.add(var18), effect, color, 1);
                    var20.subtract(var18);
                }, (int) var10);
                ++step;
            }
        }
        loc.subtract(0.0D, this.yOffset, 0.0D);
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> isAnimating = false, (int) var10 + 1);
    }

    @Override
    public void stop() {
        this.cancel();
    }

    private ArrayList<Vector> createCircle(double var1, double var3) {
        double var5 = var3 * 64.0D;
        double var7 = 6.283185307179586D / var5;
        ArrayList var9 = new ArrayList();

        for (int var10 = 0; (double) var10 < var5; ++var10) {
            double var11 = (double) var10 * var7;
            double var13 = var3 * Math.cos(var11);
            double var15 = var3 * Math.sin(var11);
            Vector var17 = new Vector(var13, var1, var15);
            var9.add(var17);
        }
        return var9;
    }
}