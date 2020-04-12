package de.md5lukas.spl;

import de.md5lukas.spl.config.SPLConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class SimplePlayerList extends JavaPlugin {

    private SPLConfig splConfig;
    private PlayerListUpdater playerListUpdater;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        splConfig = new SPLConfig(getConfig());
        playerListUpdater = new PlayerListUpdater(this);
    }

    public void reloadSPLConfig() {
        reloadConfig();
        splConfig.load(getConfig());
        playerListUpdater.restartTimer();
    }

    public SPLConfig getSPLConfig() {
        return splConfig;
    }

    public PlayerListUpdater getPlayerListUpdater() {
        return playerListUpdater;
    }
}
