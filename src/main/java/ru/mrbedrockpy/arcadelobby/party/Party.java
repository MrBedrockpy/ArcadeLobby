package ru.mrbedrockpy.arcadelobby.party;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import ru.mrbedrockpy.bedrocklib.manager.ManagerItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Party implements ManagerItem<String> {

    private Player leader;
    private final LinkedList<Player> members = new LinkedList<>();

    public Party(Player leader) {
        this.leader = leader;
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>(members);
        players.add(leader);
        return players;
    }

    @Override
    public String getId() {
        return leader.getName();
    }
}
