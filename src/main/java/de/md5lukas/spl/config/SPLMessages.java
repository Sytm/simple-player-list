package de.md5lukas.spl.config;

import de.md5lukas.spl.ColorTranslator;
import org.bukkit.configuration.ConfigurationSection;

public class SPLMessages {

    private final ColorTranslator colorTranslator;

    private String
            playerListHeader,
            playerListFooter,
            mainCommandNoPermission,
            notFound,
            noPermission,
            helpTitle,
            helpReload,
            reloadSuccess;

    public SPLMessages() {
        this.colorTranslator = new ColorTranslator();
    }

    public void load(ConfigurationSection section) {
        playerListHeader = colorTranslator.translate(section.getString("playerList.header"));
        playerListFooter = colorTranslator.translate(section.getString("playerList.footer"));
        mainCommandNoPermission = colorTranslator.translate(section.getString("commands.mainCommandNoPermission"));
        notFound = colorTranslator.translate(section.getString("commands.notFound"));
        noPermission = colorTranslator.translate(section.getString("commands.noPermission"));
        helpTitle = colorTranslator.translate(section.getString("commands.help.title"));
        helpReload = colorTranslator.translate(section.getString("commands.help.reload"));
        reloadSuccess = colorTranslator.translate(section.getString("commands.reload.success"));
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
