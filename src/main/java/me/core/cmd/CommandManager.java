package me.core.cmd;

import jdk.jshell.execution.Util;
import me.commands.Ping;
import me.util.Utils;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.*;

public class CommandManager {
    public Map<String, Command> commandMap = new LinkedHashMap<>();

    public CommandManager() {
        commandMap.put("ping", new Ping());
    }

    @EventSubscriber
    public void onMsgReceived(MessageReceivedEvent event) {
        String[] argArray;
        String commandStr;
        List<String> argsList = new ArrayList<>();

        // first check channel prefix, then guild prefix, then lastly use default
        String prefix = Utils.getPrefix(event);
        // If message doesn't start with BOT_PREFIX, check if it tags us
        if (!event.getMessage().getFormattedContent().startsWith(prefix)) {
            if (!event.getMessage().getContent().matches("<@(!)?" + event.getClient().getOurUser().getStringID() + ">.*")) {
                return; // does not start with prefix AND does not start by tagging our user
            } else { // tagged
                argArray = event.getMessage().getContent().substring(21).trim().split(" ", 2);
                if (event.getMessage().getContent().replaceFirst("!", "").substring(21).trim().equals("")) { // if no command args at all, use help command
                    commandStr = "help";
                    Utils.send(event.getChannel(), "My prefix here is : " + Utils.getPrefix(event));
                } else {
                    commandStr = argArray[0];
                }
            }
        } else { // start with bot prefix
            // Given a message "/test arg1, arg2", argArray will contain ["$test", "arg1, arg2, ...."]
            argArray = event.getMessage().getContent().split(" ", 2);
            // Extract the "command" part of the first arg out by ditching the amount of characters present in the prefix
            commandStr = argArray[0].substring(prefix.length());
        }

        // turn arg array into list
        if (argArray.length != 1) argsList.addAll(Arrays.asList(argArray[1].split(",[ ]?")));

        if (!commandMap.containsKey(commandStr)) {
            // do levenshtein
        }

        Command cmd = commandMap.get(commandStr);
        Runnable runCmd = () -> {
            cmd.execute(new Context());
        };
        // if require sync
    }


}
