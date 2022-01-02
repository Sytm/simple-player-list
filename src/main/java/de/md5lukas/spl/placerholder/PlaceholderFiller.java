package de.md5lukas.spl.placerholder;

import org.bukkit.entity.Player;

public interface PlaceholderFiller {

    default void preLoop() {
    }

    String fillPlaceholders(Player player, String string);
}
