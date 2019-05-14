package me.commands.meta;

import me.GalleryBot;
import me.core.cmd.Command;
import me.core.cmd.CommandManager;
import me.core.cmd.Context;
import me.core.error.CommandStateException;
import me.util.Config;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;

public class HelpCmd extends Command {
    public HelpCmd() {
        super("help");
    }

    @Override
    public void execute(Context context) throws CommandStateException {
        context.reply(new EmbedBuilder()
                .withTitle("GalleryBot V1.0")
                .withColor(Color.PINK)
                .withDesc(genDesc())
                .withUrl(Config.GITHUB_URL.val)
                .withFooterIcon(GalleryBot.client.getUserByID(Long.valueOf(Config.DEV_ID.val)).getAvatarURL())
                .withFooterText("Built with love."));
    }

    private String genDesc() {
        StringBuilder sb = new StringBuilder();
        CommandManager.commandMap.values()
                .forEach(cmd -> sb.append(String.format("%s\t\t%s",
                        cmd.getName(),
                        cmd.getDesc())));

        return sb.toString();
    }

    @Override
    public String getHelp() {
        return "Shows meta help for Gallery Bot";
    }

    @Override
    public String getDesc() {
        return "Shows this message";
    }
}
