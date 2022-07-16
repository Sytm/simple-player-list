package de.md5lukas.spl.config;

import org.bukkit.configuration.file.FileConfiguration;

@SuppressWarnings("ConstantConditions")
public final class SPLConfig {

    private int refreshRate;

    private boolean usePlaceholderAPI;
    private final SPLMessages splMessages;

    public SPLConfig(FileConfiguration configuration) {
        splMessages = new SPLMessages();
        load(configuration);
    }

    public void load(FileConfiguration configuration) {
        refreshRate = configuration.getInt("refreshRate");
        usePlaceholderAPI = configuration.getBoolean("usePlaceholderAPI");
        splMessages.load(configuration.getConfigurationSection("messages"));
    }

    public long getRefreshRateInTicks() {
        return refreshRate * 20L;
    }

    public boolean isUsePlaceholderAPI() {
        return usePlaceholderAPI;
    }

    public SPLMessages getSPLMessages() {
        return splMessages;
    }
}
