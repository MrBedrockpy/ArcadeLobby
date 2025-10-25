package ru.mrbedrockpy.arcadelobby.util;

import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.HSVLike;
import org.jetbrains.annotations.NotNull;
import ru.mrbedrockpy.arcadelobby.config.LangConfig;

public enum TeamColor {

    BLACK(LangConfig.Team.BLACK, 0x000000),
    DARK_BLUE(LangConfig.Team.DARK_BLUE, 0x0000aa),
    DARK_GREEN(LangConfig.Team.DARK_GREEN, 0x00aa00),
    DARK_AQUA(LangConfig.Team.DARK_AQUA, 0x00aaaa),
    DARK_RED(LangConfig.Team.DARK_RED, 0xaa0000),
    DARK_PURPLE(LangConfig.Team.DARK_PURPLE, 0xaa00aa),
    GOLD(LangConfig.Team.GOLD, 0xffaa00),
    GRAY(LangConfig.Team.GRAY, 0xaaaaaa),
    DARK_GRAY(LangConfig.Team.DARK_GRAY, 0x555555),
    BLUE(LangConfig.Team.BLUE, 0x5555ff),
    GREEN(LangConfig.Team.GREEN, 0x55ff55),
    AQUA(LangConfig.Team.AQUA, 0x55ffff),
    RED(LangConfig.Team.RED, 0xff5555),
    LIGHT_PURPLE(LangConfig.Team.LIGHT_PURPLE, 0xff55ff),
    YELLOW(LangConfig.Team.YELLOW, 0xffff55),
    WHITE(LangConfig.Team.WHITE, 0xffffff);

    private final String name;
    private final int value;
    private final TextColor textColor;
    private final HSVLike hsv;

    TeamColor(final String name, final int value) {
        this.name = name;
        this.value = value;
        this.textColor = TextColor.color(value);
        this.hsv = this.textColor.asHSV();
    }

    public TextColor asTextColor() {
        return this.textColor;
    }

    public int value() {
        return this.value;
    }

    public @NotNull HSVLike asHSV() {
        return this.hsv;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
