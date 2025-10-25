package ru.mrbedrockpy.arcadelobby;

import com.onarandombox.MultiverseCore.MultiverseCore;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import ru.mrbedrockpy.arcadelobby.command.JoinCommand;
import ru.mrbedrockpy.arcadelobby.command.PartyCommand;
import ru.mrbedrockpy.arcadelobby.command.argument.MinigameArgument;
import ru.mrbedrockpy.arcadelobby.config.LangConfig;
import ru.mrbedrockpy.arcadelobby.lobby.LobbyManager;
import ru.mrbedrockpy.arcadelobby.minigame.Minigame;
import ru.mrbedrockpy.arcadelobby.minigame.MinigameManager;
import ru.mrbedrockpy.arcadelobby.party.PartyManager;
import ru.mrbedrockpy.bedrocklib.BedrockPlugin;
import ru.mrbedrockpy.bedrocklib.config.ConfigManager;

import java.util.logging.Level;

@Getter
public final class ArcadeLobby extends BedrockPlugin<ArcadeLobby> {

    private MultiverseCore multiverseCore;

    private LiteCommands<CommandSender> liteCommands;

    private ConfigManager<ArcadeLobby> configManager;

    private LobbyManager lobbyManager;
    private PartyManager partyManager;
    private MinigameManager minigameManager;

    @Override
    protected void registerConfigs() {
        this.configManager = ConfigManager.builder()
                .withConfigs(
                        LangConfig.class
                )
                .withPluginFolder(getDataFolder())
                .build(this, getSerializeConfig());
    }

    @Override
    protected void registerManagers() {
        try {
            this.multiverseCore = getPlugin(MultiverseCore.class);
        } catch (IllegalArgumentException e) {
            getLogger().log(Level.SEVERE, "Multiverse Core not found!");
            getServer().getPluginManager().disablePlugin(this);
        }
        this.lobbyManager = new LobbyManager(this);
        this.partyManager = new PartyManager(this);
        this.minigameManager = new MinigameManager(this);
    }

    @Override
    protected void registerCommands() {
        this.liteCommands = LiteBukkitFactory.builder(this)
                .commands(
                        new PartyCommand(this),
                        new JoinCommand(this)
                )
                .argument(Minigame.class, new MinigameArgument(this))
                .settings(settings -> settings.nativePermissions(true))
                .build();
    }

    @Override
    protected void unregisterCommands() {
        if (this.liteCommands != null) this.liteCommands.unregister();
    }
}
