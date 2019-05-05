package me.core.cmd;

import me.util.Utils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;

public class Context {
    IChannel channel;

    public Context(MessageReceivedEvent event) {
        this.channel = event.getChannel();
    }


    public void reply(String message) {
        Utils.send(channel, message);
    }
}
