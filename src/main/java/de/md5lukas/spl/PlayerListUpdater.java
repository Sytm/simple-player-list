package de.md5lukas.spl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public final class PlayerListUpdater {

    private final SimplePlayerList main;
    private final TPSRetriever tpsRetriever;
    private BukkitTask currentTask;

    public PlayerListUpdater(SimplePlayerList main) {
        this.main = main;
        this.tpsRetriever = new TPSRetriever();
        restartTimer();
    }

    public void updatePlayer(Player player) {
        String header = main.getSPLConfig().getSPLMessages().getPlayerListHeader(), footer = main.getSPLConfig().getSPLMessages().getPlayerListFooter();

        header = fillPlaceholders(player, header);
        footer = fillPlaceholders(player, footer);

        player.setPlayerListHeaderFooter(header, footer);
    }

    private String[] currentTps = new String[3];

    private String fillPlaceholders(Player player, String string) {
        string = string.replace("%name%", player.getName());
        string = string.replace("%displayname%", player.getDisplayName());

        string = string.replace("%onlineplayers%", Integer.toString(Bukkit.getOnlinePlayers().size()));
        string = string.replace("%maxplayers%", Integer.toString(Bukkit.getMaxPlayers()));

        string = string.replace("%tps1%", currentTps[0]);
        string = string.replace("%tps5%", currentTps[1]);
        string = string.replace("%tps15%", currentTps[2]);
        return string;
    }

    public void restartTimer() {
        if (currentTask != null)
            currentTask.cancel();
        currentTask = main.getServer().getScheduler()
                .runTaskTimer(main, () -> {
                    currentTps = tpsRetriever.getTPSFormatted();

                    Bukkit.getOnlinePlayers().forEach(this::updatePlayer);
                }, 0, main.getSPLConfig().getRefreshRateInTicks());
    }
}
