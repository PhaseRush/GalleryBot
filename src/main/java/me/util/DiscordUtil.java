package me.util;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class DiscordUtil {

    public static String getNickOrDefault(MessageReceivedEvent event) {
        if (event.getChannel().isPrivate()) return event.getAuthor().getName();
        String nick = event.getAuthor().getNicknameForGuild(event.getGuild());
        if (nick == null) return event.getAuthor().getName();
        else return nick;
    }

    public static String getPrefix(MessageReceivedEvent event) { // cant use IID b/c need to preserve channel > guild
        if (event.getChannel().isPrivate()) return Utils.getPrefixMap().get("DEFAULT_PREFIX");
        return Utils.getPrefixMap().getOrDefault(event.getChannel().getStringID(),
                Utils.getPrefixMap().getOrDefault(event.getGuild().getStringID(),
                        Utils.getPrefixMap().get("DEFAULT_PREFIX")));
    }
}
