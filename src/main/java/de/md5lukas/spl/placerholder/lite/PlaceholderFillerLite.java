package de.md5lukas.spl.placerholder.lite;

import de.md5lukas.spl.placerholder.PlaceholderFiller;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class PlaceholderFillerLite implements PlaceholderFiller {

    private final TPSRetriever tpsRetriever;
    private final PingRetriever pingRetriever;
    private String[] currentTps;

    public PlaceholderFillerLite(Plugin plugin) {
        this.tpsRetriever = new TPSRetriever(plugin);
        this.pingRetriever = new PingRetriever(plugin);
    }

    @Override
    public void preLoop() {
        currentTps = tpsRetriever.getTPSFormatted();
    }

    @Override
    public String fillPlaceholders(Player player, String string) {
        string = string.replace("%name%", player.getName());
        string = string.replace("%displayname%", player.getDisplayName());

        string = string.replace("%onlineplayers%", Integer.toString(Bukkit.getOnlinePlayers().size()));
        string = string.replace("%maxplayers%", Integer.toString(Bukkit.getMaxPlayers()));

        string = string.replace("%tps1%", currentTps[0]);
        string = string.replace("%tps5%", currentTps[1]);
        string = string.replace("%tps15%", currentTps[2]);

        string = string.replace("%ping%", Integer.toString(pingRetriever.getPing(player)));

        return string;
    }
}
