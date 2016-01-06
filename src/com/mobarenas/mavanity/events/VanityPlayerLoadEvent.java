package com.mobarenas.mavanity.events;

import com.mobarenas.mavanity.player.PlayerProfile;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by HP1 on 12/29/2015.
 */
public class VanityPlayerLoadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final PlayerProfile profile;
    private final boolean online;

    public VanityPlayerLoadEvent(PlayerProfile profile, boolean online) {
        this.profile = profile;
        this.online = online;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public PlayerProfile getProfile() {
        return profile;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public boolean isOnline() {
        return online;
    }
}
