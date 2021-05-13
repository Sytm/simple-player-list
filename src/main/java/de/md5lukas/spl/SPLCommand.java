package de.md5lukas.spl;

import de.md5lukas.spl.config.SPLMessages;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public final class SPLCommand implements CommandExecutor {

    private final SimplePlayerList main;
    private final SPLMessages messages;

    public SPLCommand(SimplePlayerList main) {
        this.main = main;
        this.messages = main.getSPLConfig().getSPLMessages();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("simpleplayerlist.command")) {
            if (args.length == 0) {
                sendHelp(sender);
            } else {
                if ("reload".equalsIgnoreCase(args[0])) {
                    reload(sender);
                } else {
                    sendNotFound(sender);
                }
            }
        } else {
            sendNoPermission(sender);
        }
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(messages.getHelpTitle());
        if (sender.hasPermission("simpleplayerlist.reload")) {
            if (sender instanceof Player) {
                BaseComponent[] baseComponents = TextComponent.fromLegacyText(messages.getHelpReload());
                ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/simpleplayerlist reload");
                for (BaseComponent baseComponent : baseComponents)
                    baseComponent.setClickEvent(clickEvent);
                ((Player) sender).spigot().sendMessage(baseComponents);
            } else {
                sender.sendMessage(messages.getHelpReload());
            }
        }
    }

    private void sendNoPermission(CommandSender sender) {
        sender.sendMessage(messages.getNoPermission());
    }

    private void sendNotFound(CommandSender sender) {
        sender.sendMessage(messages.getNotFound());
    }

    private void reload(CommandSender sender) {
        if (sender.hasPermission("simpleplayerlist.reload")) {
            main.reloadSPLConfig();
            sender.sendMessage(messages.getReloadSuccess());
        } else {
            sendNoPermission(sender);
        }
    }
}
