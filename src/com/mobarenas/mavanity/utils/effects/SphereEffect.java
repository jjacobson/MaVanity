package com.mobarenas.mavanity.utils.effects;

import com.mobarenas.mavanity.MaVanity;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

/**
 * Created by HP1 on 1/1/2016.
 */
public class SphereEffect extends BukkitRunnable implements ParticleEffect {

    private MaVanity plugin;
    private Player player;
    private Random random;
    public double radius = 1.2D;
    public double yOffset = 0.9D;
    public int particles = 50;

    public SphereEffect(MaVanity plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.random = new Random();
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
        loc.add(0.0D, yOffset, 0.0D);
        for (int i = 0; i < particles; i++) {
            Vector localVector = getRandomVector().multiply(radius);
            loc.add(localVector);
            EffectHelper.showEffect(loc, effect, color, 1);
            loc.subtract(localVector);
        }
    }

    private Vector getRandomVector() {
        double x = random.nextDouble() * 2.0D - 1.0D;
        double y = random.nextDouble() * 2.0D - 1.0D;
        double z = random.nextDouble() * 2.0D - 1.0D;
        return (new Vector(x, y, z)).normalize();
    }

    @Override
    public void stop() {
        this.cancel();
    }

}
