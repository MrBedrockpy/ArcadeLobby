package ru.mrbedrockpy.arcadelobby.party;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class PartyInvite {

    private final Player inviter;
    private final Player target;
    private final long expireAt;

    public boolean isExpired() {
        return System.currentTimeMillis() > expireAt;
    }
}
