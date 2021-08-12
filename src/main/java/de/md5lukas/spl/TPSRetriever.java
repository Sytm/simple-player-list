package de.md5lukas.spl;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

public final class TPSRetriever {

    private Object dedicatedServer = null;
    private Field recentTpsField = null;

    public double[] getTPS() {
        try {
            if (recentTpsField == null) {
                Server bukkitServer = Bukkit.getServer();

                Field dedicatedServerField = bukkitServer.getClass().getDeclaredField("console");
                dedicatedServerField.setAccessible(true);

                dedicatedServer = dedicatedServerField.get(bukkitServer);
                recentTpsField = dedicatedServer.getClass().getSuperclass().getDeclaredField("recentTps");
                recentTpsField.setAccessible(true);
            }

            return (double[]) recentTpsField.get(dedicatedServer);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Formats the double value always to one decimal place
     * 20.0
     * 19.9
     * 19.8
     * etc
     */
    private final DecimalFormat df = new DecimalFormat("0.0");

    public String[] getTPSFormatted() {
        double[] tps = getTPS();
        return new String[] {
                df.format(Math.min(tps[0], 20)),
                df.format(Math.min(tps[1], 20)),
                df.format(Math.min(tps[2], 20)),
        };
    }
}
