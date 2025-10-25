package ru.mrbedrockpy.arcadelobby.party;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import ru.mrbedrockpy.arcadelobby.ArcadeLobby;
import ru.mrbedrockpy.arcadelobby.config.LangConfig;
import ru.mrbedrockpy.arcadelobby.util.BroadcastUtil;
import ru.mrbedrockpy.arcadelobby.util.System;
import ru.mrbedrockpy.bedrocklib.util.ChatUtil;

import java.util.HashMap;
import java.util.Map;

public class PartyManager extends System<Party, String> {

    private final Map<Player, PartyInvite> invites = new HashMap<>();

    public PartyManager(ArcadeLobby plugin) {
        super(plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void invite(Player inviter, Player target) {
        invites.put(target, new PartyInvite(inviter, target, java.lang.System.currentTimeMillis() + 60_000L));
    }

    public PartyInvite getInvite(Player player) {
        PartyInvite invite = invites.get(player);
        if (invite != null && invite.isExpired()) {
            invites.remove(player);
            return null;
        }
        return invite;
    }

    public void removeInvite(Player player) {
        invites.remove(player);
    }

    /**
     * @return true если пати было распущено
     */
    public boolean leaveParty(Player leaver) {
        Party party = getByPlayer(leaver.getName());
        if (party == null) return false;

        if (party.getLeader().equals(leaver)) {
            if (party.getMembers().isEmpty()) {
                unregister(party);
                return true;
            }

            Player newLeader = party.getMembers().removeFirst();
            String msg = ChatUtil.applyPlaceholders(
                    LangConfig.Chat.Party.LEADER_LEAVE,
                    Map.of("%old_leader%", leaver.getName(), "%new_leader%", newLeader.getName())
            );
            party.setLeader(newLeader);
            BroadcastUtil.broadcast(party, msg);
        } else {
            party.getMembers().remove(leaver);
            String msg = ChatUtil.applyPlaceholders(
                    LangConfig.Chat.Party.MEMBER_LEAVE,
                    Map.of("%member%", leaver.getName())
            );
            BroadcastUtil.broadcast(party, msg);

            if (party.getMembers().isEmpty()) {
                unregister(party);
                return true;
            }
        }

        return false;
    }

    @EventHandler
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        Player player = event.getPlayer();
        leaveParty(player);
        removeInvite(player);
    }

    public Party getByPlayer(String playerName) {
        for (Party party : getItems()) {
            if (party.getLeader().getName().equalsIgnoreCase(playerName)) return party;
            for (Player member : party.getMembers()) {
                if (member.getName().equalsIgnoreCase(playerName)) return party;
            }
        }
        return null;
    }
}
