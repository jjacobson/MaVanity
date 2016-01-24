package com.mobarenas.mavanity.listeners;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.messages.Messages;
import com.mobarenas.mavanity.messages.Pair;
import com.mobarenas.mavanity.player.PlayerProfile;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by HP1 on 1/5/2016.
 */
public class BungeeMessageListener implements PluginMessageListener {

    private MaVanity plugin;

    public BungeeMessageListener(MaVanity plugin) {
        this.plugin = plugin;
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "crate-received", this);
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        DataInputStream in = new DataInputStream(stream);
        String response = null;
        try {
            response = in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UUID uuid = UUID.fromString(response);
        Player p = plugin.getServer().getPlayer(uuid);
        PlayerProfile profile = plugin.getPlayerManager().getProfile(uuid);
        if (p != null && p.isOnline() && profile != null) {
            profile.setCrates(profile.getCrates() + 1);
            p.sendMessage(Messages.getMessage("crates.added", new Pair("%amount%", 1 + "")));
            return;
        }
        plugin.getCratesQueue().addCrates(uuid, 1);
    }
}
