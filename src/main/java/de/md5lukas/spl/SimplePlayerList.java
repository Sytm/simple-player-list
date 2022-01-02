package de.md5lukas.spl;

import de.md5lukas.spl.config.SPLConfig;
import de.md5lukas.spl.placerholder.PlaceholderAPIHook;
import de.md5lukas.spl.placerholder.PlaceholderFiller;
import de.md5lukas.spl.placerholder.lite.PlaceholderFillerLite;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("ConstantConditions")
public final class SimplePlayerList extends JavaPlugin {

    private static final int METRICS_PLUGIN_ID = 7109;

    private SPLConfig splConfig;
    private PlayerListUpdater playerListUpdater;

    private PlaceholderFiller placeholderFiller;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        splConfig = new SPLConfig(getConfig());

        placeholderFiller = PlaceholderAPIHook.getInstance(this);
        if (placeholderFiller == null) {
            placeholderFiller = new PlaceholderFillerLite(this);
        }

        playerListUpdater = new PlayerListUpdater(this);

        PluginCommand spl = getCommand("simpleplayerlist");
        spl.setExecutor(new SPLCommand(this));
        spl.setPermissionMessage(splConfig.getSPLMessages().getMainCommandNoPermission());

        initMetrics();
    }

    private void initMetrics() {
        Metrics metrics = new Metrics(this, METRICS_PLUGIN_ID);
        metrics.addCustomChart(new SimplePie("placeholderapi", () -> {
            if (placeholderFiller instanceof PlaceholderAPIHook) {
                return "true";
            } else {
                return "false";
            }
        }));
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

    public PlaceholderFiller getPlaceholderFiller() {
        return placeholderFiller;
    }

    public PlayerListUpdater getPlayerListUpdater() {
        return playerListUpdater;
    }
}
