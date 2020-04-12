package de.md5lukas.spl.config;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

@SuppressWarnings("ConstantConditions")
public class SPLMessages {

    private String
            playerListHeader,
            playerListFooter,
            mainCommandNoPermission,
            notFound,
            noPermission,
            helpTitle,
            helpReload,
            reloadSuccess;

    public void load(ConfigurationSection section) {
        char altColorChar = '&';
        playerListHeader = ChatColor.translateAlternateColorCodes(altColorChar, section.getString("playerList.header"));
        playerListFooter = ChatColor.translateAlternateColorCodes(altColorChar, section.getString("playerList.footer"));
        mainCommandNoPermission = ChatColor.translateAlternateColorCodes(altColorChar, section.getString("commands.mainCommandNoPermission"));
        notFound = ChatColor.translateAlternateColorCodes(altColorChar, section.getString("commands.notFound"));
        noPermission = ChatColor.translateAlternateColorCodes(altColorChar, section.getString("commands.noPermission"));
        helpTitle = ChatColor.translateAlternateColorCodes(altColorChar, section.getString("commands.help.title"));
        helpReload = ChatColor.translateAlternateColorCodes(altColorChar, section.getString("commands.help.reload"));
        reloadSuccess = ChatColor.translateAlternateColorCodes(altColorChar, section.getString("commands.reload.success"));
    }

    public String getPlayerListHeader() {
        return playerListHeader;
    }

    public String getPlayerListFooter() {
        return playerListFooter;
    }

    public String getMainCommandNoPermission() {
        return mainCommandNoPermission;
    }

    public String getNotFound() {
        return notFound;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getHelpTitle() {
        return helpTitle;
    }

    public String getHelpReload() {
        return helpReload;
    }

    public String getReloadSuccess() {
        return reloadSuccess;
    }
}
