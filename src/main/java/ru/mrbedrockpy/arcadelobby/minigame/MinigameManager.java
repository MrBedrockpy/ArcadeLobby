package ru.mrbedrockpy.arcadelobby.minigame;

import ru.mrbedrockpy.arcadelobby.ArcadeLobby;
import ru.mrbedrockpy.arcadelobby.config.LangConfig;
import ru.mrbedrockpy.arcadelobby.lobby.Lobby;
import ru.mrbedrockpy.arcadelobby.lobby.LobbyManager;
import ru.mrbedrockpy.arcadelobby.party.Party;
import ru.mrbedrockpy.arcadelobby.util.System;

import java.util.ArrayList;
import java.util.List;

public class MinigameManager extends System<Minigame, String> {

    public MinigameManager(ArcadeLobby plugin) {
        super(plugin);
    }

    public void joinMinigame(Minigame minigame, Party party) {
        LobbyManager lobbyManager = getPlugin().getLobbyManager();
        if (party.getPlayers().size() > minigame.getCountPlayersInTeam()) {
            party.getLeader().sendMessage(LangConfig.Chat.Party.PARTY_TOO_BIG);
            return;
        }
        for (Lobby lobby : getLobbiesByMinigame(minigame))
            if (lobbyManager.tryJoin(lobby, party)) return;
        lobbyManager.tryJoin(lobbyManager.createNewLobby(minigame), party);
    }

    private List<Lobby> getLobbiesByMinigame(Minigame minigame) {
        List<Lobby> lobbies = new ArrayList<>();
        for (Lobby lobby : getPlugin().getLobbyManager().getItems()) {
            if (lobby.getMinigame().equals(minigame)) lobbies.add(lobby);
        }
        return lobbies;
    }
}
