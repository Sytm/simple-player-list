package de.md5lukas.spl;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SPLListener implements Listener {

    private final SimplePlayerList main;

    public SPLListener(SimplePlayerList main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        main.getPlayerListUpdater().updatePlayer(event.getPlayer());
    }
}
