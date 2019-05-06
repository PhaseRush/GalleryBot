package me.commands;

import me.core.cmd.Command;
import me.core.cmd.Context;
import me.core.error.CommandStateException;
import me.core.error.MissingPermissionsException;
import me.core.permission.Permission;

import java.util.Arrays;

public class Ping extends Command {

    public Ping() {
        super(Permission.USER, Arrays.asList("ping"), "ping");
    }


    @Override
    public void execute(Context context) throws CommandStateException, MissingPermissionsException {
        context.reply("ping");
    }

    @Override
    public boolean canRun(Context context) {
        return true;
    }

    @Override
    public String getHelp() {
        return null;
    }
}
