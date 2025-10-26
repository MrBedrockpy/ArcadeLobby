package ru.mrbedrockpy.arcadelobby.lobby;

import org.bukkit.World;
import org.bukkit.entity.Player;
import ru.mrbedrockpy.arcadelobby.minigame.Minigame;
import ru.mrbedrockpy.arcadelobby.minigame.Team;

import java.util.List;

public class DefaultLobby extends Lobby {

    public DefaultLobby(String id, Minigame minigame, World world, List<Team> teams) {
        super(id, minigame, world, teams);
    }

    @Override
    public void onJoin(Player player) {

    }

    @Override
    public void onLeave(Player player) {

    }

    @Override
    public void onStart(Runnable startGame) {
        startGame.run();
    }
}
