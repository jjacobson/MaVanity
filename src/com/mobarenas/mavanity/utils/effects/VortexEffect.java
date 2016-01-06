package com.mobarenas.mavanity.utils.effects;

import com.mobarenas.mavanity.MaVanity;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by HP1 on 1/1/2016.
 */
public class VortexEffect extends BukkitRunnable implements ParticleEffect {

    private MaVanity plugin;
    private Player player;
    private boolean isAnimating;
    public final int strands = 2;
    public final int particles = 170;
    public final float radius = 1.3F;
    public final float curve = 2.0F;
    public final double rotation = 0.7853981633974483D;

    public VortexEffect(MaVanity plugin, Player player) {
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
        if (isAnimating) {
            return;
        }
        isAnimating = true;
        double var5 = 3.5D;
        double var7 = 0.0D;
        double var9 = 0.0D;
        double var11 = 0.13;
        for (int var15 = 1; var15 <= this.strands; ++var15) {
            var7 = 1.0D;
            double var13 = var5;
            for (int var16 = 1; var16 <= this.particles; ++var16) {
                float var17 = (float) var16 / (float) this.particles;
                double var18 = (double) (this.curve * var17 * 2.0F) * 3.141592653589793D / (double) this.strands + 6.283185307179586D * (double) var15 / (double) this.strands + this.rotation;
                final double var20 = Math.cos(var18) * (double) var17 * (double) this.radius;
                final double var22 = Math.sin(var18) * (double) var17 * (double) this.radius;
                var13 -= 0.02D;
                var7 += var11;
                var9 += var11;
                final double var13Y = var13;
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                    Location loc = player.getLocation();
                    loc.add(var20, var13Y, var22);
                    EffectHelper.showEffect(loc, effect, color, 1);
                }, (int) var7);
            }
        }
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> isAnimating = false, (int) (var9 / 2.0D));
    }

    @Override
    public void stop() {
        this.cancel();
    }
}