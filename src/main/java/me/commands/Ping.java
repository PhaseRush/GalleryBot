package me.commands;

import me.core.cmd.Command;
import me.core.cmd.Context;
import me.core.error.CommandStateException;
import me.core.error.MissingPermissionsException;
import me.core.permission.Permission;
import me.util.Utils;
import sx.blah.discord.handle.obj.IMessage;

import java.util.Arrays;

public class Ping extends Command {

    public Ping() {
        super(Permission.USER, Arrays.asList("ping"), "ping");
    }

    @Override
    public void execute(Context context) throws CommandStateException, MissingPermissionsException {
        long gatewayPing = System.currentTimeMillis() - context.getMessage().getTimestamp().toEpochMilli();

        long startSend = System.currentTimeMillis();
        IMessage msg = Utils.sendGet(context.getChannel(), "Meow!");
        long restPing = msg.getTimestamp().toEpochMilli() - startSend;

        msg.edit("My ping is " + (restPing + gatewayPing) + " ms." + "\n```js\n" +
                "Gateway: " + gatewayPing +
                "ms\nRest: " + restPing + "ms```");
    }

    @Override
    public String getHelp() {
        return "Displays my ping";
    }
}
