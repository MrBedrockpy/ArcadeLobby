package ru.mrbedrockpy.arcadelobby.lobby;

import lombok.Getter;
import org.bukkit.World;
import org.bukkit.entity.Player;
import ru.mrbedrockpy.arcadelobby.minigame.Minigame;
import ru.mrbedrockpy.arcadelobby.minigame.Team;
import ru.mrbedrockpy.arcadelobby.party.Party;
import ru.mrbedrockpy.bedrocklib.manager.ManagerItem;

import java.util.ArrayList;
import java.util.List;

public abstract class Lobby implements ManagerItem<String> {

    @Getter private final String id;
    @Getter private final Minigame minigame;
    @Getter private final World world;
    private final List<Player> soloPlayers;
    private final List<Team> teams;

    public Lobby(String id, Minigame minigame, World world, List<Team> teams) {
        this.id = id;
        this.minigame = minigame;
        this.world = world;
        this.teams = new ArrayList<>(teams);
        this.soloPlayers = new ArrayList<>();
    }

    public void addSoloPlayer(Player player) {
        soloPlayers.add(player);
        player.teleport(world.getSpawnLocation());
        this.onJoin(player);
    }

    public void addParty(Team team, Party party) {
        team.addParty(party);
        party.getPlayers().forEach(this::onJoin);
    }

    public void removeSoloPlayer(Player player) {
        soloPlayers.remove(player);
        for (Team team : teams) team.removePlayer(player);
        this.onLeave(player);
    }

    public List<Player> getSoloPlayers() {
        return new ArrayList<>(soloPlayers);
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>(soloPlayers);
        for (Team team : teams) players.addAll(team.getMembers());
        return players;
    }

    public List<Team> getTeams() {
        return new ArrayList<>(teams);
    }

    public abstract void onJoin(Player player);

    public abstract void onLeave(Player player);

    public abstract void onStart(Runnable startGame);
}
