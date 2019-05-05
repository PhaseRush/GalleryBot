package me.commands;

import me.core.cmd.Command;
import me.core.cmd.Context;

public class Ping extends Command {

    @Override
    public void execute(Context context) {
        context.reply("ping");
    }

    @Override
    public boolean canRun(Context context) {
        return false;
    }

    @Override
    public String getHelp() {
        return null;
    }
}
