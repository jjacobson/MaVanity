package com.mobarenas.mavanity.utils.effects;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.utils.WingHelper;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Created by HP1 on 1/1/2016.
 */
public class WingsEffect extends BukkitRunnable implements ParticleEffect {

    private MaVanity plugin;
    private Player player;
    private Location to;
    private Location from;
    private Vector x, y, z;

    public WingsEffect(MaVanity plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.runTaskTimer(plugin, 0L, 8L);
    }

    @Override
    public void run() {
        if (player == null || !player.isOnline()) {
            this.stop();
            return;
        }
        Effect effect = plugin.getPlayerManager().getProfile(player.getUniqueId()).getEffect();
        Color color = plugin.getPlayerManager().getProfile(player.getUniqueId()).getColor();
        to = getBehind(player.getLocation(), 0.45D);
        from = player.getLocation();
        z = to.toVector().subtract(from.toVector());
        double d1 = z.length();
        double d2 = 0.0D;
        d2 += d1 / 2.0D;
        z.multiply(1.0D / d1);
        Vector vec = new Vector(-z.getZ(), 0.0D, z.getX());
        double d3 = vec.length();
        if (d3 == 0.0D) {
            vec.setX(1.0D);
            vec.setZ(0.0D);
        } else {
            vec.multiply(1.0D / d3);
        }
        Vector localVector2 = vec.clone();
        localVector2.crossProduct(z);
        double d4 = Math.sin(d2);
        double d5 = Math.cos(d2);
        x = new Vector(d5 * vec.getX() - d4 * localVector2.getX(), d5 * vec.getY() - d4 * localVector2.getY(), d5 * vec.getZ() - d4 * localVector2.getZ());
        y = new Vector(d4 * vec.getX() + d5 * localVector2.getX(), d4 * vec.getY() + d5 * localVector2.getY(), d4 * vec.getZ() + d5 * localVector2.getZ());
        for (Vector v : WingHelper.getWingPoints()) {
            translateEffect(effect, color, to, v, x, y, z);
        }
    }

    private void translateEffect(Effect effect, Color color, Location location, Vector vec, Vector x, Vector y, Vector z) {
        location = location.clone();
        Vector var7 = new Vector(vec.getX() * x.getX() + vec.getZ() * z.getX(), vec.getY(), vec.getX() * x.getZ() + vec.getZ() * z.getZ());
        location.setX(var7.getX() + location.getX());
        location.setY(var7.getY() + location.getY());
        location.setZ(var7.getZ() + location.getZ());
        EffectHelper.showEffect(location, effect, color, 1);
    }


    @Override
    public void stop() {
        this.cancel();
    }

    private Location getBehind(Location location, double dist) {
        Location loc = location.subtract(location.getDirection().normalize().multiply(dist).setY(0.0D));
        loc.setY(location.getY());
        return loc;
    }
}
