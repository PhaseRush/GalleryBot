package me.core.cmd;

import me.commands.meta.Fetch;
import me.commands.meta.Ping;
import me.core.error.CommandStateException;
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
        commandMap.put("fetch", new Fetch());
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
                if (event.getMessage().getContent().replaceFirst(prefix, "").substring(21).trim().equals("")) { // if no command args at all, use help command
                    commandStr = "help";
                    Utils.send(event.getChannel(), "My prefix here is : " + prefix);
                } else {
                    commandStr = argArray[0];
                }
            }
        } else { // start with bot prefix
            // Given a message "!gg test arg1 ", argArray will contain ["!gg", "test, "arg1, arg2, ...."]
            argArray = event.getMessage().getContent().split(" ", 3);
            // Extract the "command" part of the first arg out by ditching the amount of characters present in the prefix
            try {
                commandStr = argArray[1];
            } catch (ArrayIndexOutOfBoundsException ignored) {
                commandStr = "help"; // happens when someone ONLY sends prefix, so just treat like they need help
            }
        }

        // turn arg array into list
        if (argArray.length > 2) argsList.addAll(Arrays.asList(argArray[2].split(",[ ]?")));

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
        final Context finalContext = new Context(event, finalTargetCmd.getName(), argsList);
        // check if can run
        if (!finalTargetCmd.canRun(finalContext)) { // not enough permissions, or any other restriction
            finalContext.reply(String.format("You don't have permission to run `%s`", targetCmd.getName()));
            return;
        }
        Runnable execution = () -> {
            String error = "";
            try {
                finalTargetCmd.execute(finalContext);
            } catch (CommandStateException throwable) {
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
