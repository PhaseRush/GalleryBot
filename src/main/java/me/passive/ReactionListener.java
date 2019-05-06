package me.passive;

import me.GalleryBot;
import me.util.Emojis;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

public class ReactionListener {

    private static IChannel roleReactionChannel;
    private static IMessage roleReactionMsg;

    @EventSubscriber
    public void onMsgReceived(ReactionEvent event) {
        // first time check
        if (roleReactionChannel == null) {
            roleReactionChannel = GalleryBot.client.getChannelByID(574866380726206464L);
            roleReactionMsg = roleReactionChannel.getFullMessageHistory().stream().filter(msg -> msg.getStringID().equals("574870334491328512")).findFirst().get();
        }
        if (event.getMessage().equals(roleReactionMsg)) {
            handleRoleReactions(event);
        }
    }

    private void handleRoleReactions(ReactionEvent event) {
        if (event instanceof ReactionAddEvent) {
            if (Emojis.A.equals(event.getReaction().getEmoji())) {
                event.getUser().addRole(event.getGuild().getRolesByName("Artist").get(0));
            } else if (Emojis.P.equals(event.getReaction().getEmoji())) {
                event.getUser().addRole(event.getGuild().getRolesByName("Patron").get(0));
            }
        } else { // ReactionRemoveEvent
            if (Emojis.A.equals(event.getReaction().getEmoji())) {
                event.getUser().removeRole(event.getGuild().getRolesByName("Artist").get(0));
            } else if (Emojis.P.equals(event.getReaction().getEmoji())) {
                event.getUser().removeRole(event.getGuild().getRolesByName("Patron").get(0));
            }
        }
    }
}
