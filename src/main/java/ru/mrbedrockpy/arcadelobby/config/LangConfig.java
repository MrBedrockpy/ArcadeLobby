package ru.mrbedrockpy.arcadelobby.config;

import ru.mrbedrockpy.bedrocklib.config.annotation.Config;
import ru.mrbedrockpy.bedrocklib.config.annotation.ConfigField;

@Config(name = "lang.yml")
public class LangConfig {

    @Config(name = "chat")
    public static class Chat {

        @Config(name = "party")
        public static class Party {

            @ConfigField(name = "leader-leave")
            public static String LEADER_LEAVE = "| &e%old_leader% is leave, new leader %new_leader%!";

            @ConfigField(name = "member-leave")
            public static String MEMBER_LEAVE = "| &e%member% is leave!";

            @ConfigField(name = "party-too-big")
            public static String PARTY_TOO_BIG = "| &eYour party is too big!";

        }

    }

    @Config(name = "command")
    public static class Command {

        @ConfigField(name = "join-success")
        public static String JOIN_SUCCESS = "| &aJoin success!";

        @Config(name = "party")
        public static class Party {

            @ConfigField(name = "player-already-in-party") public static String PLAYER_ALREADY_IN_PARTY = "| &cYou already in a party!";
            @ConfigField(name = "party-successful-created") public static String PARTY_SUCCESSFUL_CREATED = "| &aParty successfully created!";
            @ConfigField(name = "player-not-in-party") public static String PLAYER_NOT_IN_PARTY = "| &cYou are not in a party!";
            @ConfigField(name = "party-not-found") public static String PARTY_NOT_FOUND = "| &cParty not found!";
            @ConfigField(name = "already-in-this-party") public static String ALREADY_IN_THIS_PARTY = "| &cYou are already in this party!";
            @ConfigField(name = "party-dissolved") public static String PARTY_DISSOLVED = "| &eYou left the party, it was dissolved.";
            @ConfigField(name = "member-leave") public static String MEMBER_LEAVE = "| &e%member% has left the party.";
            @ConfigField(name = "not-leader") public static String NOT_LEADER = "| &cOnly the party leader can do this!";
            @ConfigField(name = "member-not-found") public static String MEMBER_NOT_FOUND = "| &cPlayer not found or not in your party!";
            @ConfigField(name = "member-kicked") public static String MEMBER_KICKED = "| &e%member% was kicked from the party!";
            @ConfigField(name = "you-were-kicked") public static String YOU_WERE_KICKED = "| &cYou were kicked from the party!";
            @ConfigField(name = "invite-sent") public static String INVITE_SENT = "| &aYou invited %target% to your party!";
            @ConfigField(name = "invite-already-sent") public static String INVITE_ALREADY_SENT = "| &eYou already sent an invite to this player!";
            @ConfigField(name = "no-invite") public static String NO_INVITE = "| &cYou have no pending invites!";
            @ConfigField(name = "join-party-success") public static String JOIN_PARTY_SUCCESS = "| &a%member% joined the party!";
            @ConfigField(name = "you-joined-party") public static String YOU_JOINED_PARTY = "| &aYou joined the party!";
            @ConfigField(name = "leave-success") public static String LEAVE_SUCCESS = "| &eYou left the party.";

            @ConfigField(name = "invite-title") public static String INVITE_TITLE = "✉ ";
            @ConfigField(name = "invite-message") public static String INVITE_MESSAGE = "%leader% invited you to join their party!";
            @ConfigField(name = "invite-button-accept") public static String INVITE_BUTTON_ACCEPT = "[Accept]";
            @ConfigField(name = "invite-button-deny") public static String INVITE_BUTTON_DENY = "[Decline]";
            @ConfigField(name = "invite-hover-accept") public static String INVITE_HOVER_ACCEPT = "Click to join the party";
            @ConfigField(name = "invite-hover-deny") public static String INVITE_HOVER_DENY = "Click to decline the invite";
            @ConfigField(name = "invite-accepted-leader") public static String INVITE_ACCEPTED_LEADER = "| &a%member% accepted your invite!";
            @ConfigField(name = "invite-denied-leader") public static String INVITE_DENIED_LEADER = "| &e%member% declined your invite.";
            @ConfigField(name = "party-list-header") public static String PARTY_LIST_HEADER = "&6--- Party ---";
            @ConfigField(name = "party-list-leader") public static String PARTY_LIST_LEADER = "&eLeader: &f%leader%";
            @ConfigField(name = "party-list-members") public static String PARTY_LIST_MEMBERS = "&eMembers: &f%members%";
            @ConfigField(name = "party-list-empty-members") public static String PARTY_LIST_EMPTY_MEMBERS = "&7none";
        }

        @Config(name = "dto-not-found")
        public static class DtoNotFound {

            @ConfigField(name = "minigame")
            public static String MINIGAME = "| &cMinigame not found!";

        }
    }

    @Config(name = "team")
    public static class Team {

        @ConfigField(name = "black") public static String BLACK = "BLACK";
        @ConfigField(name = "dark-blue") public static String DARK_BLUE = "DARK_BLUE";
        @ConfigField(name = "dark-green") public static String DARK_GREEN = "DARK_GREEN";
        @ConfigField(name = "dark-aqua") public static String DARK_AQUA = "DARK_AQUA";

        @ConfigField(name = "dark-red") public static String DARK_RED = "DARK_RED";
        @ConfigField(name = "dark-purple") public static String DARK_PURPLE = "DARK_PURPLE";
        @ConfigField(name = "gold") public static String GOLD = "GOLD";
        @ConfigField(name = "gray") public static String GRAY = "GRAY";

        @ConfigField(name = "dark-gray") public static String DARK_GRAY = "DARK_GRAY";
        @ConfigField(name = "blue") public static String BLUE = "BLUE";
        @ConfigField(name = "green") public static String GREEN = "GREEN";
        @ConfigField(name = "aqua") public static String AQUA = "AQUA";

        @ConfigField(name = "red") public static String RED = "RED";
        @ConfigField(name = "light-purple") public static String LIGHT_PURPLE = "LIGHT_PURPLE";
        @ConfigField(name = "yellow") public static String YELLOW = "YELLOW";
        @ConfigField(name = "white") public static String WHITE = "WHITE";

    }

}
