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
public class SpiralEffect extends BukkitRunnable implements ParticleEffect {

    private MaVanity plugin;
    private Player player;
    private boolean isAnimating;
    private final double yStep = 0.09D;
    private final double var13 = 0.2617993877991494D;
    private final double delay = 1.4;

    public SpiralEffect(MaVanity plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.runTaskTimer(plugin, 0L, 10L);
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
        double var5 = 0.0D;
        double var7 = 0.0D;
        double y = 0.15D;
        var5 += delay * 2.0D;
        double var16;
        for (int i = 0; i < 24; ++i) {
            y += yStep;
            var16 = (double) i * var13;
            var7 += delay;
            var5 += delay;
            final double v16 = var16;
            final double nY = y;
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                double x = player.getLocation().getX() + 0.77D * Math.cos(v16);
                double z = player.getLocation().getZ() + 0.77D * Math.sin(v16);
                Location location = new Location(player.getWorld(), x, player.getLocation().getY() + nY, z);
                EffectHelper.showEffect(location, effect, color, 1);
            }, (int) var5);
        }
        for (int i = 0; i < 24; ++i) {
            y -= yStep;
            var16 = (double) i * var13;
            var7 += delay;
            var5 += delay;
            final double v16 = var16;
            final double nY = y;
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                double x = player.getLocation().getX() + 0.77D * Math.cos(v16);
                double z = player.getLocation().getZ() + 0.77D * Math.sin(v16);
                Location location = new Location(player.getWorld(), x, player.getLocation().getY() + nY, z);
                EffectHelper.showEffect(location, effect, color, 1);
            }, (int) var5);
        }
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> isAnimating = false, (int) (var7));
    }

    @Override
    public void stop() {
        this.cancel();
    }
}
