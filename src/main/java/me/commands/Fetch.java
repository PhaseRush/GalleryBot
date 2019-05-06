package me.commands;

import me.core.cmd.Command;
import me.core.cmd.Context;
import me.core.error.CommandStateException;
import me.core.error.MissingPermissionsException;
import me.core.permission.Permission;
import me.util.Utils;

import java.util.Collections;

public class Fetch extends Command {

    public Fetch() {
        super(Permission.DEV, Collections.emptyList(), "fetch");
    }

    @Override
    public void execute(Context context) throws CommandStateException, MissingPermissionsException {
//        IMessage targetMsg = context.getClient().getMessageByID(context.getMessage().getLongID());
//        List<IRole> roles = context.getGuild().getRoles();

        Utils.LOG.info("paused execution");
    }

    @Override
    public String getHelp() {
        return null;
    }
}
