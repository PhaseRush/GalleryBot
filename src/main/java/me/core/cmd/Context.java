package me.core.cmd;

import me.util.Utils;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import java.util.List;

public class Context {
    private List<String> params;
    private String cmdName;
    private MessageReceivedEvent event;

    public Context(MessageReceivedEvent event, String cmdName, List<String> params) {
        this.params = params;
        this.cmdName = cmdName;
        this.event = event;
    }

    public void reply(String message) {
        Utils.send(event.getChannel(), message);
    }

    public void reply(EmbedBuilder eb) {
        Utils.send(event.getChannel(), eb);
    }

    public IChannel getChannel() {
        return event.getChannel();
    }

    public IGuild getGuild() {
        return event.getGuild();
    }

    public IMessage getMessage() {
        return event.getMessage();
    }

    public IUser getUser() {
        return getMessage().getAuthor();
    }

    public boolean isPrivate() {
        return event.getGuild() == null;
    }

    public IDiscordClient getClient() {
        return event.getClient();
    }

    public List<String> getParams() {
        return params;
    }

    public String getCmdName() {
        return cmdName;
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }
}
