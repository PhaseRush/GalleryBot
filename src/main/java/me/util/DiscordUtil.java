package me.util;

import me.GalleryBot;
import me.core.permission.Permission;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IIDLinkedObject;
import sx.blah.discord.handle.obj.IUser;

public class DiscordUtil {

    public static String getNickOrDefault(IUser user, IGuild guild) {
        String nick = user.getNicknameForGuild(guild);
        if (nick == null) return user.getName();
        else return nick;
    }
    public static String getNickOrDefault(MessageReceivedEvent event) {
        if (event.getChannel().isPrivate()) return event.getAuthor().getName();
        return getNickOrDefault(event.getAuthor(), event.getGuild());
    }

    public static String getPrefix(MessageReceivedEvent event) { // cant use IID b/c need to preserve channel > guild
        if (event.getChannel().isPrivate()) return Config.DEFAULT_PREFIX.val;
        return Utils.getPrefixMap().getOrDefault(event.getChannel().getStringID(),
                Utils.getPrefixMap().getOrDefault(event.getGuild().getStringID(),
                        Config.DEFAULT_PREFIX.val));
    }

    public static Permission getPerm(IUser user) {
        if (user.getStringID().equals(Config.DEV_ID.val)) {
            return Permission.DEV;
        } else if (user.getRolesForGuild(GalleryBot.client.getGuildByID(Long.valueOf(Config.GUILD_ID.val))).stream()
                .map(IIDLinkedObject::getStringID)
                .anyMatch(roleId -> roleId.equals(Config.GIGAMOD_ROLE_ID.val))) {
            return Permission.ADMIN;
        } else {
            return Permission.USER;
        }
    }
}
