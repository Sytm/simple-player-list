package de.md5lukas.spl.placerholder.lite;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

public final class PingRetriever {

    private final Plugin plugin;
    private boolean reflectionFailed = false;
    private boolean getPingTried = false;

    private Method getPing = null;
    private Method getHandle = null;
    private Field pingField = null;

    public PingRetriever(Plugin plugin) {
        this.plugin = plugin;
    }

    public int getPing(Player player) {
        if (reflectionFailed) {
            return -1;
        }
        if (getPing == null && !getPingTried) {
            getPingTried = true;
            try {
                getPing = player.getClass().getMethod("getPing");
            } catch (NoSuchMethodException ignored) {
            }
        }
        try {
            if (getPing != null) {
                return (int) getPing.invoke(player);
            }

            if (getHandle == null)
                getHandle = player.getClass().getDeclaredMethod("getHandle");

            Object entityPlayer = getHandle.invoke(player);

            if (pingField == null)
                pingField = entityPlayer.getClass().getField("ping");

            return pingField.getInt(entityPlayer);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not initialize Player Ping retriever", e);
            reflectionFailed = true;
            return -1;
        }
    }
}
