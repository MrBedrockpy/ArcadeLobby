package ru.mrbedrockpy.arcadelobby.util;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.mrbedrockpy.arcadelobby.party.Party;
import ru.mrbedrockpy.bedrocklib.util.ChatUtil;

import java.util.List;

@UtilityClass
public class BroadcastUtil {

    public void broadcast(List<Player> players, String message) {
        players.forEach(player -> player.sendMessage(ChatUtil.format(message)));
    }

    public void broadcast(@NotNull Party party, @NotNull String message) {
        party.getPlayers().forEach(p -> p.sendMessage(ChatUtil.format(message)));
    }
}
