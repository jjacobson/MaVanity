package com.mobarenas.mavanity.utils.effects;

import com.mobarenas.mavanity.MaVanity;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

/**
 * Created by HP1 on 1/1/2016.
 */
public class BodyEffect extends BukkitRunnable implements ParticleEffect {

    private MaVanity plugin;
    private Player player;
    private Random random;

    public BodyEffect(MaVanity plugin, Player player) {
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
        for (int i = 0; i < 10; i++) {
            Location location = player.getLocation().add(getRandomChange(), getRandomChange() + 1.2, getRandomChange());
            EffectHelper.showEffect(location, effect, color, 1);
        }
    }

    @Override
    public void stop() {
        this.cancel();
    }

    private double getRandomChange() {
        return (double) (random.nextInt(12) - 6) / 10.0;
    }
}