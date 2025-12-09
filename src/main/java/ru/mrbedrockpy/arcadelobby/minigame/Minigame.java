package ru.mrbedrockpy.arcadelobby.minigame;

import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import ru.mrbedrockpy.arcadelobby.lobby.Lobby;
import ru.mrbedrockpy.bedrocklib.manager.ManagerItem;

public interface Minigame extends ManagerItem<String> {

    int getCountPlayersInTeam();

    Lobby getLobby(String id);

    default MultiverseWorld cloneWorld(MVWorldManager worldManager, String newName, MultiverseWorld template) {
        worldManager.cloneWorld(template.getName(), newName);
        return worldManager.getMVWorld(newName);
    }

    void startGame(Lobby lobby);
}
