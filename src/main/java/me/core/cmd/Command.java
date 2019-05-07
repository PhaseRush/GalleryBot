package me.core.cmd;

import me.core.error.CommandStateException;
import me.core.permission.Permission;
import me.util.Utils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class Command {
    private final String name;
    private final List<String> aliases;
    private final Permission permission;

    public Command(Permission permission, List<String> aliases, String name) {
        this.name = name;
        this.aliases = aliases;
        this.permission = permission;
    }

    // execute the command
    public abstract void execute(Context context) throws CommandStateException;

    // By default, do simple permission check on the required perm. subclasses can override and add more restrictions
    public boolean canRun(Context context) {
        return Utils.getPerm(context.getMessage().getAuthor()).isAdequate(permission);
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
