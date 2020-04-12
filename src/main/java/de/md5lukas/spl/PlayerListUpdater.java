package de.md5lukas.spl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class PlayerListUpdater {

    private final SimplePlayerList main;
    private BukkitTask currentTask;

    public PlayerListUpdater(SimplePlayerList main) {
        this.main = main;
        restartTimer();
    }

    public void updatePlayer(Player player) {
        String header = main.getSPLConfig().getSPLMessages().getPlayerListHeader(), footer = main.getSPLConfig().getSPLMessages().getPlayerListFooter();

        header = fillPlaceholders(player, header);
        footer = fillPlaceholders(player, footer);

        player.setPlayerListHeaderFooter(header, footer);
    }

    private String fillPlaceholders(Player player, String string) {
        string = string.replace("%onlineplayers%", Integer.toString(Bukkit.getOnlinePlayers().size()));
        string = string.replace("%maxonlineplayers%", Integer.toString(Bukkit.getMaxPlayers()));
        return string;
    }

    public void restartTimer() {
        if (currentTask != null)
            currentTask.cancel();
        currentTask = main.getServer().getScheduler().runTaskTimer(main, () -> {
            Bukkit.getOnlinePlayers().forEach(this::updatePlayer);
        }, 0, main.getSPLConfig().getRefreshRateInTicks());
    }
}
