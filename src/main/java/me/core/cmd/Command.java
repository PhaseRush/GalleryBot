package me.core.cmd;

import java.util.Collections;
import java.util.Set;

public abstract class Command {

    // execute the command
    public abstract void execute(Context context);

    // can a command run
    public abstract boolean canRun(Context context);

    // help message
    public abstract String getHelp();

    // by default everything allowed
    public Set<String> autoCorrectBlackList() {
        return Collections.emptySet();
    }
}
