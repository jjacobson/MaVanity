package com.mobarenas.mavanity.player;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.events.VanityPlayerLoadEvent;
import com.mobarenas.mavanity.mysql.MySQL;
import com.mobarenas.mavanity.utils.effects.EffectType;
import com.mobarenas.mavanity.utils.effects.ParticleEffect;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by HP1 on 12/26/2015.
 */
public class PlayerManager {

    private MaVanity plugin;
    private MySQL mySQL;
    private Map<UUID, PlayerProfile> profiles;

    public PlayerManager(MaVanity plugin) {
        this.plugin = plugin;
        this.profiles = new HashMap<>();
        try {
            this.mySQL = new MySQL(plugin);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadProfile(UUID uuid, boolean online) {
        load(uuid, online);
    }

    public PlayerProfile getProfile(UUID uuid) {
        return profiles.get(uuid);
    }

    public Map<UUID, PlayerProfile> getProfiles() {
        return profiles;
    }

    public void removePlayer(UUID uuid, boolean asyncSave) {
        Player player = plugin.getServer().getPlayer(uuid);
        ParticleEffect effect = plugin.getParticleManager().getRunning().get(uuid);
        if (effect != null) {
            effect.stop();
            plugin.getParticleManager().getRunning().remove(uuid);
        }
        if (player != null) {
            plugin.getSettingsManager().removeDisabledDisguise(player);
            plugin.getSettingsManager().removeDisabledParticles(player);
        }
        plugin.getParticleManager().getRunning().remove(uuid);
        if (asyncSave) {
            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> savePlayer(uuid, asyncSave));
        } else {
            savePlayer(uuid, asyncSave);
        }
    }

    public void addPlayer(UUID uuid, PlayerProfile profile) {
        profiles.put(uuid, profile);
    }

    private void load(UUID uuid, boolean online) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try (PreparedStatement st = mySQL.getConnection().prepareStatement("SELECT * FROM MaVanity WHERE uuid=?")) {
                st.setString(1, uuid.toString());
                PlayerProfile profile = new PlayerProfile(uuid);
                ResultSet set = st.executeQuery();
                while (set.next()) {
                    profile.setPlayersVisible(set.getBoolean("players_visible"));
                    profile.setPetsVisible(set.getBoolean("pets_visible"));
                    profile.setDisguisesVisible(set.getBoolean("disguises_visible"));
                    profile.setParticlesVisible(set.getBoolean("particles_visible"));
                    String et = set.getString("effect_type");
                    profile.setEffectType(et == null ? null : EffectType.valueOf(et));
                    String ef = set.getString("effect");
                    profile.setEffect(ef == null ? null : Effect.valueOf(ef));
                    String color = set.getString("color");
                    profile.setCrates(set.getInt("crates"));
                    if (color == null) {
                        profile.setColor(null);
                    } else {
                        String[] split = color.split(":");
                        Color c = Color.fromRGB(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                        profile.setColor(c);
                    }
                }
                plugin.getServer().getScheduler().runTask(plugin, () -> {
                    addPlayer(uuid, profile);
                    VanityPlayerLoadEvent event = new VanityPlayerLoadEvent(profile, online);
                    plugin.getServer().getPluginManager().callEvent(event);
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void savePlayer(UUID uuid, boolean async) {
        PlayerProfile profile = profiles.get(uuid);
        try (PreparedStatement st = mySQL.getConnection().prepareStatement(
                "INSERT INTO MaVanity (uuid, players_visible, pets_visible, disguises_visible, particles_visible, effect_type, effect, color, crates) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                        "players_visible=VALUES(players_visible), " +
                        "pets_visible=VALUES(pets_visible), " +
                        "disguises_visible=VALUES(disguises_visible), " +
                        "particles_visible=VALUES(particles_visible), " +
                        "effect_type=VALUES(effect_type), " +
                        "effect=VALUES(effect), " +
                        "color=VALUES(color), " +
                        "crates=VALUES(crates)")) {
            st.setString(1, uuid.toString());
            st.setBoolean(2, profile.isPlayersVisible());
            st.setBoolean(3, profile.isPetsVisible());
            st.setBoolean(4, profile.isDisguisesVisible());
            st.setBoolean(5, profile.isParticlesVisible());
            st.setString(6, profile.getEffectType() == null ? null : profile.getEffectType().toString());
            st.setString(7, profile.getEffect() == null ? null : profile.getEffect().toString());
            st.setString(8, colorToString(profile.getColor()));
            st.setInt(9, profile.getCrates());
            st.execute();
            if (async) {
                plugin.getServer().getScheduler().runTask(plugin, () -> profiles.remove(uuid));
            } else {
                profiles.remove(uuid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String colorToString(Color color) {
        if (color == null) {
            return null;
        }
        return color.getRed() + ":" + color.getGreen() + ":" + color.getBlue();
    }
}
