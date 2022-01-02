package de.md5lukas.spl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public final class PlayerListUpdater implements Runnable {

    private final SimplePlayerList main;
    private BukkitTask currentTask;

    public PlayerListUpdater(SimplePlayerList main) {
        this.main = main;
        restartTimer();
    }

    public void updatePlayer(Player player) {
        String header = main.getSPLConfig().getSPLMessages().getPlayerListHeader(), footer = main.getSPLConfig().getSPLMessages().getPlayerListFooter();

        header = main.getPlaceholderFiller().fillPlaceholders(player, header);
        footer = main.getPlaceholderFiller().fillPlaceholders(player, footer);

        player.setPlayerListHeaderFooter(header, footer);
    }

    @Override
    public void run() {
        main.getPlaceholderFiller().preLoop();
        Bukkit.getOnlinePlayers().forEach(this::updatePlayer);
    }

    public void restartTimer() {
        if (currentTask != null)
            currentTask.cancel();
        currentTask = main.getServer().getScheduler()
                .runTaskTimer(main, this, 0, main.getSPLConfig().getRefreshRateInTicks());
    }
}
