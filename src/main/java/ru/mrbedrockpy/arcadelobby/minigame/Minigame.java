package ru.mrbedrockpy.arcadelobby.minigame;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.World;
import ru.mrbedrockpy.arcadelobby.lobby.DefaultLobby;
import ru.mrbedrockpy.arcadelobby.lobby.Lobby;
import ru.mrbedrockpy.bedrocklib.manager.ManagerItem;

import java.util.List;

public interface Minigame extends ManagerItem<String> {

    int getCountPlayersInTeam();

    MultiverseWorld getWaitLobbyTemplate();

    List<Team> createTeams();

     default Lobby getLobby(String id, World cbWorld, List<Team> teams) {
         return new DefaultLobby(id, this, cbWorld, teams);
     }

    void startGame(Lobby lobby);

}
