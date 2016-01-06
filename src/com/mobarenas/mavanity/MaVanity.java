package com.mobarenas.mavanity;

import com.mobarenas.mavanity.commands.AddCratesCommand;
import com.mobarenas.mavanity.listeners.*;
import com.mobarenas.mavanity.menu.MenuManager;
import com.mobarenas.mavanity.messages.Messages;
import com.mobarenas.mavanity.player.PlayerManager;
import com.mobarenas.mavanity.player.PlayerProfile;
import com.mobarenas.mavanity.rewards.CrateManager;
import com.mobarenas.mavanity.rewards.CratesQueue;
import com.mobarenas.mavanity.utils.EntityHider;
import com.mobarenas.mavanity.utils.WingHelper;
import com.mobarenas.mavanity.utils.effects.EffectHelper;
import com.mobarenas.mavanity.utils.managers.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by HP1 on 12/22/2015.
 */
public class MaVanity extends JavaPlugin {

    private MenuManager menuManager;
    private DisguiseManager disguiseManager;
    private ArmorManager armorManager;
    private PetManager petManager;
    private HatManager hatManager;
    private SettingsManager settingsManager;
    private ParticleManager particleManager;
    private PlayerManager playerManager;
    private CratesQueue cratesQueue;
    private EntityHider entityHider;
    private CrateManager crateManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadHelpers();
        registerListeners();
        registerCommands();
        for (Player player : getServer().getOnlinePlayers()) {
            getPlayerManager().loadProfile(player.getUniqueId(), true);
        }
    }

    @Override
    public void onDisable() {
        for (Player player : getServer().getOnlinePlayers()) {
            getPlayerManager().removePlayer(player.getUniqueId(), false);
        }
        for (UUID uuid : getParticleManager().getRunning().keySet()) {
            getParticleManager().getRunning().get(uuid).stop();
            getParticleManager().getRunning().remove(uuid);
        }
    }

    private void loadHelpers() {
        try {
            new Messages(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new EffectHelper(this);
        new WingHelper(this);
        new BungeeMessageListener(this);
        cratesQueue = new CratesQueue(this);
        crateManager = new CrateManager(this);
        playerManager = new PlayerManager(this);
        entityHider = new EntityHider(this, EntityHider.Policy.BLACKLIST);
        menuManager = new MenuManager(this);
        disguiseManager = new DisguiseManager(this);
        armorManager = new ArmorManager(this);
        petManager = new PetManager(this);
        hatManager = new HatManager(this);
        settingsManager = new SettingsManager(this);
        particleManager = new ParticleManager(this);
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerClickListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerLoadedListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PetSpawnListener(this), this);
    }

    private void registerCommands() {
        this.getCommand("addcrates").setExecutor(new AddCratesCommand(this));
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public DisguiseManager getDisguiseManager() {
        return disguiseManager;
    }

    public PetManager getPetManager() {
        return petManager;
    }

    public ArmorManager getArmorManager() {
        return armorManager;
    }

    public HatManager getHatManager() {
        return hatManager;
    }

    public ParticleManager getParticleManager() {
        return particleManager;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public CratesQueue getCratesQueue() {
        return cratesQueue;
    }

    public EntityHider getEntityHider() {
        return entityHider;
    }

    public CrateManager getCrateManager() {
        return crateManager;
    }
}
