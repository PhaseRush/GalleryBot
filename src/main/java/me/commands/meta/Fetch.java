package me.commands.meta;

import me.core.cmd.Command;
import me.core.cmd.Context;
import me.core.error.CommandStateException;
import me.core.permission.Permission;
import me.util.Utils;

import java.util.Collections;
import java.util.UUID;

public class Fetch extends Command {

    public Fetch() {
        super(Permission.DEV, Collections.emptyList(), "fetch");
    }

    @Override
    public void execute(Context context) throws CommandStateException {
//        IMessage targetMsg = context.getClient().getMessageByID(context.getMessage().getLongID());
//        List<IRole> roles = context.getGuild().getRoles();

        //context.reply("paused exec");
        context.getChannel().sendMessage("test\t" + UUID.randomUUID());
        Utils.LOG.info("paused execution");
    }

    @Override
    public boolean requireSync() {
        return true;
    }

    @Override
    public String getHelp() {
        return "Used for testing purposes";
    }

    @Override
    public String getDesc() {
        return "testing purposes";
    }
}
