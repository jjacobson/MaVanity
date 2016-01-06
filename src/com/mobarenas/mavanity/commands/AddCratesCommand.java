package com.mobarenas.mavanity.commands;

import com.mobarenas.mavanity.MaVanity;
import com.mobarenas.mavanity.messages.Messages;
import com.mobarenas.mavanity.messages.Pair;
import com.mobarenas.mavanity.player.PlayerProfile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by HP1 on 12/29/2015.
 */
public class AddCratesCommand implements CommandExecutor {

    private MaVanity plugin;

    public AddCratesCommand(MaVanity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (!sender.hasPermission("mobarena.vanity.crates")) {
            sender.sendMessage(Messages.getMessage("crates.no-perms"));
            return true;
        }
        UUID uuid = UUID.fromString(args[0]);
        int crates = Integer.parseInt(args[1]);
        Player player = plugin.getServer().getPlayer(uuid);
        if (player != null && player.isOnline()) { // player is already online, just give them the crates
            PlayerProfile profile = plugin.getPlayerManager().getProfile(uuid);
            profile.setCrates(profile.getCrates() + crates);
            player.sendMessage(Messages.getMessage("crates.added", new Pair("%amount%", crates + "")));
            System.out.println("Gave " + crates + " crates to " + player.getName());
            return true;
        }
        System.out.println("Added " + crates + " crates to " + args[0]);
        plugin.getPlayerManager().loadProfile(uuid, false);
        plugin.getCratesQueue().addCrates(uuid, crates);
        return true;
    }
}
