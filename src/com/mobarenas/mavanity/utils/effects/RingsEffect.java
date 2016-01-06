package com.mobarenas.mavanity.utils.effects;

import com.mobarenas.mavanity.MaVanity;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Created by HP1 on 1/1/2016.
 */
public class RingsEffect extends BukkitRunnable implements ParticleEffect {

    private MaVanity plugin;
    private Player player;
    private boolean isAnimating;
    private final double angularVelocity = 0.039269908169872414D;
    private final int particlesOrbital = 200;
    private final int radius = 1;
    private final double rotation = 90.0D;

    public RingsEffect(MaVanity plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.runTaskTimer(plugin, 0L, 20L);
    }

    @Override
    public void run() {
        if (player == null || !player.isOnline()) {
            this.stop();
            return;
        }
        Effect effect = plugin.getPlayerManager().getProfile(player.getUniqueId()).getEffect();
        Color color = plugin.getPlayerManager().getProfile(player.getUniqueId()).getColor();
        int orbitals = 2;
        int step = 0;
        if (isAnimating) {
            return;
        }
        isAnimating = true;
        Location location = player.getLocation().add(0.0D, 1.15D, 0.0D);
        double var6 = 0.0D;
        double var8 = 0.0D;
        double delay = 0.13;

        for (int i = 0; i < particlesOrbital; i++) {
            double var13 = (double) step * angularVelocity;
            for (int var15 = 0; var15 < orbitals; ++var15) {
                double var16 = 3.141592653589793D / (double) orbitals * (double) var15;
                Vector var18 = (new Vector(Math.cos(var13), Math.sin(var13), 0.0D)).multiply(radius);
                rotateAroundAxisX(var18, var16);
                rotateAroundAxisY(var18, rotation);
                location.add(var18);
                var6 += delay;
                var8 += delay;
                final Location var19 = location.clone();
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> EffectHelper.showEffect(var19, effect, color, 1), (int) (var6));
                location.subtract(var18);
            }
            ++step;
        }
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> isAnimating = false, (int) (var8 / 2.0D));
    }

    @Override
    public void stop() {
        this.cancel();
    }

    public static final Vector rotateAroundAxisX(Vector vec, double d) {
        double var7 = Math.cos(d);
        double var9 = Math.sin(d);
        double var3 = vec.getY() * var7 - vec.getZ() * var9;
        double var5 = vec.getY() * var9 + vec.getZ() * var7;
        return vec.setY(var3).setZ(var5);
    }

    public static final Vector rotateAroundAxisY(Vector vec, double d) {
        double var7 = Math.cos(d);
        double var9 = Math.sin(d);
        double var3 = vec.getX() * var7 + vec.getZ() * var9;
        double var5 = vec.getX() * -var9 + vec.getZ() * var7;
        return vec.setX(var3).setZ(var5);
    }
}
