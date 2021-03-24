package de.md5lukas.spl;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorTranslator {

    private final Pattern rgbPattern = Pattern.compile("(\\\\?)&(#[0-9a-fA-F]{6})");
    private final boolean rgbSupported;

    public ColorTranslator() {
        boolean localRgbSupported;
        try {
            ChatColor.of("#FFFFFF");
            localRgbSupported = true;
        } catch (NoSuchMethodError e) {
            localRgbSupported = false;
        }
        rgbSupported = localRgbSupported;
    }

    public boolean isRGBSupported() {
        return rgbSupported;
    }

    public String translate(String string) {
        string = ChatColor.translateAlternateColorCodes('&', string);

        if (isRGBSupported()) {
            return translateHexCodes(string);
        }

        return string;
    }

    private String translateHexCodes(String string) {
        Matcher matcher = rgbPattern.matcher(string);

        StringBuffer builder = new StringBuffer();

        while (matcher.find()) {
            boolean shouldReplace = matcher.group(1) == null || matcher.group(1).length() == 0;

            if (shouldReplace) {
                String replacement = ChatColor.of(matcher.group(2)).toString();
                matcher.appendReplacement(builder, replacement);
            } else {
                matcher.appendReplacement(builder, "&$2");
            }
        }

        matcher.appendTail(builder);

        return builder.toString();
    }
}
