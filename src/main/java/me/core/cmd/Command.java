package me.core.cmd;

import java.util.Collections;
import java.util.Set;

public abstract class Command {
    String name;
    Set<String> aliases;

    // execute the command
    public abstract void execute(Context context);

    // can a command run
    public boolean canRun(Context context) {
        return true;
    }

    // can be spell checked
    public boolean canAutoCorrect() {
        return true;
    }

    // requires sync
    public boolean requireSync() {
        return false;
    }

    // help message
    public abstract String getHelp();

    // by default everything allowed
    public Set<String> autoCorrectBlackList() {
        return Collections.emptySet();
    }

    public String getName() {
        return name;
    }
}
