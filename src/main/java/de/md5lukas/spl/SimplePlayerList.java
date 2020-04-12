package de.md5lukas.spl;

import de.md5lukas.spl.config.SPLConfig;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("ConstantConditions")
public class SimplePlayerList extends JavaPlugin {

    private SPLConfig splConfig;
    private PlayerListUpdater playerListUpdater;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        splConfig = new SPLConfig(getConfig());
        playerListUpdater = new PlayerListUpdater(this);

        PluginCommand spl = getCommand("simpleplayerlist");
        spl.setExecutor(new SPLCommand(this));
        spl.setPermissionMessage(splConfig.getSPLMessages().getMainCommandNoPermission());
    }

    public void reloadSPLConfig() {
        reloadConfig();
        splConfig.load(getConfig());
        playerListUpdater.restartTimer();
        getCommand("simpleplayerlist").setPermissionMessage(splConfig.getSPLMessages().getMainCommandNoPermission());
    }

    public SPLConfig getSPLConfig() {
        return splConfig;
    }

    public PlayerListUpdater getPlayerListUpdater() {
        return playerListUpdater;
    }
}
