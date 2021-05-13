package de.md5lukas.spl;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

public final class TPSRetriever {

    private final String version;

    public TPSRetriever() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        version = packageName.substring(packageName.lastIndexOf('.') + 1);

        craftServer = getCB("CraftServer");
        craftServerGetServer = getMethod(craftServer, "getServer");
        Class<?> minecraftServer = getNMS("MinecraftServer");
        minecraftServerRecentTPS = getField(minecraftServer, "recentTps");
    }

    private Class<?> getNMS(String name) {
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Class<?> getCB(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Method getMethod(Class<?> clazz, String name) {
        try {
            return clazz.getMethod(name);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private Field getField(Class<?> clazz, String name) {
        try {
            return clazz.getField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private final Class<?> craftServer;
    private final Method craftServerGetServer;
    private final Field minecraftServerRecentTPS;

    private Object minecraftServerInstance = null;

    private Object getMinecraftServer() {
        if (minecraftServerInstance == null) {
            Server server = Bukkit.getServer();

            if (!craftServer.isInstance(server)) {
                throw new RuntimeException("The server is not of type CraftServer");
            }

            Object craftServerInstance = craftServer.cast(server);

            try {
                minecraftServerInstance = craftServerGetServer.invoke(craftServerInstance);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        return minecraftServerInstance;
    }

    public double[] getTPS() {
        try {
            return (double[]) minecraftServerRecentTPS.get(getMinecraftServer());
        } catch (IllegalAccessException e) {
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
