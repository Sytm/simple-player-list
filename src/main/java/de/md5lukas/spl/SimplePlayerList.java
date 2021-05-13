package de.md5lukas.spl;

import de.md5lukas.spl.config.SPLConfig;
import org.bstats.bukkit.MetricsLite;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("ConstantConditions")
public final class SimplePlayerList extends JavaPlugin {

    private static final int METRICS_PLUGIN_ID = 7109;

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

        new MetricsLite(this, METRICS_PLUGIN_ID);
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
