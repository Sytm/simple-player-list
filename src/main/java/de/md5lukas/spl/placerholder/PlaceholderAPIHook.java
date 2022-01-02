package de.md5lukas.spl.placerholder;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class PlaceholderAPIHook implements PlaceholderFiller {

    private PlaceholderAPIHook() {
    }

    public static PlaceholderFiller getInstance(Plugin plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            return null;
        }
        return new PlaceholderAPIHook();
    }

    @Override
    public String fillPlaceholders(Player player, String string) {
        return PlaceholderAPI.setPlaceholders(player, string);
    }
}
