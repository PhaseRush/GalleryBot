package me.commands.meta;

import me.core.cmd.Command;
import me.core.cmd.Context;
import me.util.Utils;
import sx.blah.discord.handle.obj.IMessage;

public class Ping extends Command {

    public Ping() {
        super("ping");
    }

    @Override
    public void execute(Context context) {
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
        return "!gg ping";
    }

    @Override
    public String getDesc() {
        return "Shows the ping from the `server -> bot`, and `bot -> server -> bot`";
    }
}
