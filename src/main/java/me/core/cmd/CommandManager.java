package me.core.cmd;

import me.commands.Ping;
import me.core.error.CommandStateException;
import me.core.error.MissingPermissionsException;
import me.util.DiscordUtil;
import me.util.Utils;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandManager {
    public static Map<String, Command> commandMap = new LinkedHashMap<>();

    public CommandManager() {
        commandMap.put("ping", new Ping());
    }

    // sync executor
    private final static ExecutorService SYNC = Executors.newFixedThreadPool(1);
    // general executor
    private final static ExecutorService EXECUTORS = Executors.newCachedThreadPool();

    @EventSubscriber
    public void onMsgReceived(MessageReceivedEvent event) {
        String[] argArray;
        String commandStr;
        List<String> argsList = new ArrayList<>();

        // first check channel prefix, then guild prefix, then lastly use default
        String prefix = DiscordUtil.getPrefix(event);
        // If message doesn't start with BOT_PREFIX, check if it tags us
        if (!event.getMessage().getFormattedContent().startsWith(prefix)) {
            if (!event.getMessage().getContent().matches("<@(!)?" + event.getClient().getOurUser().getStringID() + ">.*")) {
                return; // does not start with prefix AND does not start by tagging our user
            } else { // tagged
                argArray = event.getMessage().getContent().substring(21).trim().split(" ", 2);
                if (event.getMessage().getContent().replaceFirst("!", "").substring(21).trim().equals("")) { // if no command args at all, use help command
                    commandStr = "help";
                    Utils.send(event.getChannel(), "My prefix here is : " + DiscordUtil.getPrefix(event));
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

        Command targetCmd = commandMap.get(commandStr);
        if (targetCmd == null) {
            if (commandStr.length() < 2) return;

            Optional<Command> correctedCmd = Utils.cmdSpellCorrect(commandStr);
            if (correctedCmd.isPresent()) {
                targetCmd = correctedCmd.get();
                Utils.send(event.getChannel(), "That command doesnt exist - instead executing `" + targetCmd.getName() + "`");
            } else return;
        }

        final Command finalTargetCmd = targetCmd; // need for lambda
        final Context finalContext = new Context(event);
        Runnable execution = () -> {
            String error = "";
            try {
                finalTargetCmd.execute(finalContext);
            } catch (CommandStateException | MissingPermissionsException throwable) {
                error = String.format(" with error %s", throwable.getMessage());
            } finally{
                Utils.LOG.info(String.format("CMD : %s (%d) ran %s in %s (%d)",
                        DiscordUtil.getNickOrDefault(event),
                        event.getAuthor().getLongID(),
                        finalTargetCmd.getName(),
                        event.getChannel().getName(),
                        event.getChannel().getLongID())
                        + error);
            }

        };

        // if require sync
        if (finalTargetCmd.requireSync()) {
            SYNC.execute(execution);
        } else {
            EXECUTORS.execute(execution);
        }
    }

}
