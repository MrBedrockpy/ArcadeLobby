package ru.mrbedrockpy.arcadelobby.command.argument;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import ru.mrbedrockpy.arcadelobby.ArcadeLobby;
import ru.mrbedrockpy.arcadelobby.config.LangConfig;
import ru.mrbedrockpy.arcadelobby.minigame.Minigame;
import ru.mrbedrockpy.arcadelobby.minigame.MinigameManager;
import ru.mrbedrockpy.bedrocklib.manager.ManagerItem;
import ru.mrbedrockpy.bedrocklib.util.ChatUtil;

public class MinigameArgument extends ArgumentResolver<CommandSender, Minigame> {

    private final MinigameManager minigameManager;

    public MinigameArgument(ArcadeLobby plugin) {
        this.minigameManager = plugin.getMinigameManager();
    }

    @Override
    protected ParseResult<Minigame> parse(Invocation<CommandSender> invocation, Argument<Minigame> context, String argument) {
        Minigame minigame = minigameManager.getById(argument);
        if (minigame == null) return ParseResult.failure(ChatUtil.format(LangConfig.Command.DtoNotFound.MINIGAME));
        return ParseResult.success(minigame);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Minigame> argument, SuggestionContext context) {
        return minigameManager.getItems().stream().map(ManagerItem::getId).collect(SuggestionResult.collector());
    }
}
