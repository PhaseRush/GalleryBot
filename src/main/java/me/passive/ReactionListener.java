package me.passive;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionEvent;

public class ReactionListener {

    private static String roleMessagePlaceholder = "";

    @EventSubscriber
    public void onMsgReceived(ReactionEvent event) {
        if (event.getMessage().getStringID().equals(roleMessagePlaceholder)) {
            handleRoleReactions(event);
        }
    }

    private void handleRoleReactions(ReactionEvent event) {
        if (event instanceof ReactionAddEvent) {
            // give role
        } else { // ReactionRemoveEvent
            // remove role
        }
    }
}
