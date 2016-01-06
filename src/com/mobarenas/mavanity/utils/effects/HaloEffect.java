package com.mobarenas.mavanity.utils.effects;

import com.mobarenas.mavanity.MaVanity;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by HP1 on 12/25/2015.
 */
public class HaloEffect extends BukkitRunnable implements ParticleEffect {

    private MaVanity plugin;
    private Player player;
    private boolean isAnimating;

    public HaloEffect(MaVanity plugin, Player player) {
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
        double d1 = 0.0D;
        double d2 = 0.0D;
        double d3 = 1.3D;
        double d4 = 0.3926990816987241D;
        for (int j = 0; j < 16; j++) {
            final double d5 = j * d4;
            d2 += d3;
            d1 += d3;
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                double d11 = player.getLocation().getX() + 0.4D * Math.cos(d5);
                double d21 = player.getLocation().getZ() + 0.4D * Math.sin(d5);
                EffectHelper.showEffect(new Location(player.getLocation().getWorld(), d11, player.getLocation().getY() + 2.2D, d21), effect, color, 10);
            }, (int) d1);
        }
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> isAnimating = false, (int) (d2));
    }

    @Override
    public void stop() {
        this.cancel();
    }
}
