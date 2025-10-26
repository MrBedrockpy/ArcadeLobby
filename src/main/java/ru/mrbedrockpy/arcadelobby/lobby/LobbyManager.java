package ru.mrbedrockpy.arcadelobby.lobby;

import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.Nullable;
import ru.mrbedrockpy.arcadelobby.ArcadeLobby;
import ru.mrbedrockpy.arcadelobby.minigame.Minigame;
import ru.mrbedrockpy.arcadelobby.minigame.Team;
import ru.mrbedrockpy.arcadelobby.party.Party;
import ru.mrbedrockpy.arcadelobby.util.System;

import java.util.List;

public class LobbyManager extends System<Lobby, String> {

    public LobbyManager(ArcadeLobby plugin) {
        super(plugin);
    }

    public Lobby createNewLobby(Minigame minigame) {
        String id = getFreeId();
        MultiverseWorld template = minigame.getWaitLobbyTemplate();
        MVWorldManager worldManager = getPlugin().getMultiverseCore().getMVWorldManager();
        worldManager.cloneWorld(template.getName(), id);
        MultiverseWorld world = worldManager.getMVWorld(id);
        Lobby lobby = minigame.getLobby(id, world.getCBWorld(), minigame.createTeams());
        register(lobby);
        return lobby;
    }

    public boolean tryJoin(Lobby lobby, Party party) {
        if (lobby.getPlayers().size() + party.getPlayers().size() < lobby.getMinigame().getCountPlayersInTeam() * lobby.getTeams().size()) return false;
        if (lobby.getMinigame().getCountPlayersInTeam() < party.getPlayers().size()) return false;
        if (party.getMembers().isEmpty()) {
            lobby.addSoloPlayer(party.getLeader());
            checkStartGame(lobby);
            return true;
        }
        List<Team> teams = lobby.getTeams();
        for (Team team : teams) {
            if (team.getMembers().size() + party.getPlayers().size() > lobby.getMinigame().getCountPlayersInTeam()) continue;
            lobby.addParty(team, party);
            checkStartGame(lobby);
            return true;
        }
        return false;
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Lobby lobby = getLobbyByPlayer(player);
        if (lobby == null) return;
        lobby.removeSoloPlayer(player);
    }

    private String getFreeId() {
        String id;
        int i = 0;
        do {
            i++;
            id = "lobby-" + i;
        } while (getById(id) != null);
        return id;
    }

    private void checkStartGame(Lobby lobby) {
        if (lobby.getPlayers().size() < lobby.getMinigame().getCountPlayersInTeam() * lobby.getTeams().size()) return;
        for (Player player : lobby.getSoloPlayers()) {
            for (Team team : lobby.getTeams()) {
                if (team.getMembers().size() + 1 > lobby.getMinigame().getCountPlayersInTeam()) continue;
                team.addPlayer(player);
            }
        }
        if (lobby.getPlayers().size() != lobby.getMinigame().getCountPlayersInTeam() * lobby.getTeams().size()) return;
        lobby.onStart(() -> lobby.getMinigame().startGame(lobby));
    }

    @Nullable
    public Lobby getLobbyByPlayer(Player player) {
        for (Lobby lobby : getItems()) {
            if (lobby.getPlayers().contains(player)) return lobby;
        }
        return null;
    }
}
