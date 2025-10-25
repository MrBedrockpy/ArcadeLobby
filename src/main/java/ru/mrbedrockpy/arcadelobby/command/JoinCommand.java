package ru.mrbedrockpy.arcadelobby.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;
import ru.mrbedrockpy.arcadelobby.ArcadeLobby;
import ru.mrbedrockpy.arcadelobby.config.LangConfig;
import ru.mrbedrockpy.arcadelobby.minigame.Minigame;
import ru.mrbedrockpy.arcadelobby.party.Party;
import ru.mrbedrockpy.bedrocklib.manager.Manager;

@Command(name = "join")
public class JoinCommand extends Manager<ArcadeLobby> {

    public JoinCommand(ArcadeLobby plugin) {
        super(plugin);
    }

    @Execute
    public String joinMinigame(@Context Player player, @Arg Minigame minigame) {
        Party party = getPlugin().getPartyManager().getByPlayer(player.getName());
        if (party == null) party = new Party(player);
        getPlugin().getMinigameManager().joinMinigame(minigame, party);
        return LangConfig.Command.JOIN_SUCCESS;
    }

}
