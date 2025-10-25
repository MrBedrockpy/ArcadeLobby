package ru.mrbedrockpy.arcadelobby.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.mrbedrockpy.arcadelobby.ArcadeLobby;
import ru.mrbedrockpy.arcadelobby.config.LangConfig;
import ru.mrbedrockpy.arcadelobby.party.Party;
import ru.mrbedrockpy.arcadelobby.party.PartyInvite;
import ru.mrbedrockpy.arcadelobby.party.PartyManager;
import ru.mrbedrockpy.bedrocklib.manager.Manager;
import ru.mrbedrockpy.bedrocklib.util.ChatUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Command(name = "party")
public class PartyCommand extends Manager<ArcadeLobby> {

    private final PartyManager partyManager;

    public PartyCommand(ArcadeLobby plugin) {
        super(plugin);
        this.partyManager = plugin.getPartyManager();
    }

    @Execute(name = "create")
    public String create(@Sender Player sender) {
        if (partyManager.getByPlayer(sender.getName()) != null)
            return ChatUtil.format(LangConfig.Command.Party.PLAYER_ALREADY_IN_PARTY);

        Party party = new Party(sender);
        partyManager.register(party);

        return ChatUtil.format(LangConfig.Command.Party.PARTY_SUCCESSFUL_CREATED);
    }

    @Execute(name = "join")
    public String join(@Sender Player sender, @Arg Player target) {
        Party party = partyManager.getByPlayer(sender.getName());
        if (party == null) return ChatUtil.format(LangConfig.Command.Party.PLAYER_NOT_IN_PARTY);
        if (!party.getLeader().equals(sender)) return ChatUtil.format(LangConfig.Command.Party.NOT_LEADER);
        if (partyManager.getInvite(target) != null) return ChatUtil.format(LangConfig.Command.Party.INVITE_ALREADY_SENT);

        partyManager.invite(sender, target);

        Map<String, String> placeholders = Map.of("%leader%", sender.getName());

        // Сообщение приглашённому игроку
        target.sendMessage(
                Component.text(ChatUtil.format(LangConfig.Command.Party.INVITE_TITLE), NamedTextColor.GOLD)
                        .append(Component.text(ChatUtil.applyPlaceholders(LangConfig.Command.Party.INVITE_MESSAGE, placeholders), NamedTextColor.YELLOW))
                        .appendNewline()
                        .append(
                                Component.text(ChatUtil.format(LangConfig.Command.Party.INVITE_BUTTON_ACCEPT), NamedTextColor.GREEN)
                                        .hoverEvent(HoverEvent.showText(Component.text(ChatUtil.format(LangConfig.Command.Party.INVITE_HOVER_ACCEPT))))
                                        .clickEvent(ClickEvent.runCommand("/party accept " + sender.getName()))
                        )
                        .append(Component.text(" "))
                        .append(
                                Component.text(ChatUtil.format(LangConfig.Command.Party.INVITE_BUTTON_DENY), NamedTextColor.RED)
                                        .hoverEvent(HoverEvent.showText(Component.text(ChatUtil.format(LangConfig.Command.Party.INVITE_HOVER_DENY))))
                                        .clickEvent(ClickEvent.runCommand("/party deny " + sender.getName()))
                        )
        );

        Map<String, String> map = Map.of("%target%", target.getName());
        return ChatUtil.applyPlaceholders(LangConfig.Command.Party.INVITE_SENT, map);
    }

    @Execute(name = "accept")
    public String accept(@Sender Player sender, @Arg String inviterName) {
        PartyInvite invite = partyManager.getInvite(sender);
        if (invite == null || !invite.getInviter().getName().equalsIgnoreCase(inviterName))
            return ChatUtil.format(LangConfig.Command.Party.NO_INVITE);

        Party party = partyManager.getByPlayer(invite.getInviter().getName());
        if (party == null) {
            partyManager.removeInvite(sender);
            return ChatUtil.format(LangConfig.Command.Party.PARTY_NOT_FOUND);
        }

        if (party.getPlayers().contains(sender))
            return ChatUtil.format(LangConfig.Command.Party.ALREADY_IN_THIS_PARTY);

        party.getMembers().add(sender);
        partyManager.removeInvite(sender);

        Map<String, String> placeholders = Map.of("%member%", sender.getName());
        String joinMessage = ChatUtil.applyPlaceholders(LangConfig.Command.Party.JOIN_PARTY_SUCCESS, placeholders);
        for (Player p : party.getPlayers()) p.sendMessage(joinMessage);

        return ChatUtil.format(LangConfig.Command.Party.YOU_JOINED_PARTY);
    }

    @Execute(name = "deny")
    public String deny(@Sender Player sender, @Arg String inviterName) {
        PartyInvite invite = partyManager.getInvite(sender);
        if (invite == null || !invite.getInviter().getName().equalsIgnoreCase(inviterName))
            return ChatUtil.format(LangConfig.Command.Party.NO_INVITE);

        partyManager.removeInvite(sender);

        Map<String, String> placeholders = Map.of("%member%", sender.getName());
        invite.getInviter().sendMessage(ChatUtil.applyPlaceholders(LangConfig.Command.Party.INVITE_DENIED_LEADER, placeholders));

        return ChatUtil.format("&cYou declined the invite from &f" + inviterName);
    }

    @Execute(name = "leave")
    public String leave(@Sender Player sender) {
        Party party = partyManager.getByPlayer(sender.getName());
        if (party == null) return ChatUtil.format(LangConfig.Command.Party.PLAYER_NOT_IN_PARTY);

        boolean dissolved = partyManager.leaveParty(sender);
        if (dissolved) return ChatUtil.format(LangConfig.Command.Party.PARTY_DISSOLVED);

        Map<String, String> placeholders = Map.of("%member%", sender.getName());
        return ChatUtil.applyPlaceholders(LangConfig.Command.Party.LEAVE_SUCCESS, placeholders);
    }

    @Execute(name = "kick")
    public String kick(@Sender Player sender, @Arg Player target) {
        Party party = partyManager.getByPlayer(sender.getName());
        if (party == null) return ChatUtil.format(LangConfig.Command.Party.PLAYER_NOT_IN_PARTY);
        if (!party.getLeader().equals(sender)) return ChatUtil.format(LangConfig.Command.Party.NOT_LEADER);
        if (target == null || !party.getMembers().contains(target))
            return ChatUtil.format(LangConfig.Command.Party.MEMBER_NOT_FOUND);

        party.getMembers().remove(target);
        target.sendMessage(ChatUtil.format(LangConfig.Command.Party.YOU_WERE_KICKED));

        Map<String, String> placeholders = Map.of("%member%", target.getName());
        String msg = ChatUtil.applyPlaceholders(LangConfig.Command.Party.MEMBER_KICKED, placeholders);
        for (Player player : party.getPlayers()) player.sendMessage(msg);

        return msg;
    }

    @Execute(name = "list")
    public String list(@Sender Player sender) {
        Party party = partyManager.getByPlayer(sender.getName());
        if (party == null) return ChatUtil.format(LangConfig.Command.Party.PLAYER_NOT_IN_PARTY);

        String members = party.getMembers().isEmpty()
                ? LangConfig.Command.Party.PARTY_LIST_EMPTY_MEMBERS
                : party.getMembers().stream().map(Player::getName).collect(Collectors.joining(", "));

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("%leader%", party.getLeader().getName());
        placeholders.put("%members%", members);

        return ChatUtil.format(LangConfig.Command.Party.PARTY_LIST_HEADER + "\n" +
                ChatUtil.applyPlaceholders(LangConfig.Command.Party.PARTY_LIST_LEADER, placeholders) + "\n" +
                ChatUtil.applyPlaceholders(LangConfig.Command.Party.PARTY_LIST_MEMBERS, placeholders));
    }
}
