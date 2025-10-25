package ru.mrbedrockpy.arcadelobby.minigame;

import lombok.Getter;
import org.bukkit.entity.Player;
import ru.mrbedrockpy.arcadelobby.party.Party;
import ru.mrbedrockpy.arcadelobby.util.TeamColor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Team {

    private final String name;
    private final TeamColor color;
    private final List<Player> members;

    public Team(TeamColor color) {
        this.name = color.name();
        this.color = color;
        this.members = new ArrayList<>();
    }

    public void addParty(Party party) {
        members.addAll(party.getPlayers());
    }

    public void addPlayer(Player player) {
        members.add(player);
    }
    public void removePlayer(Player player) {
        members.remove(player);
    }
}
