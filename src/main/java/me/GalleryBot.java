package me;

import com.google.gson.Gson;
import me.core.cmd.CommandManager;
import me.passive.ReactionListener;
import me.util.Config;
import me.util.Utils;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

import java.util.Locale;

public class GalleryBot {
    private final static Gson gson = new Gson();

    public static IDiscordClient client;

    public static void main(String[] args) {
        Utils.LOG.info("GalleryBot starting ...");
        Locale.setDefault(Locale.CANADA);
        Runtime.getRuntime().addShutdownHook(new Thread(GalleryBot::onShutdown));

        client = new ClientBuilder()
                .withToken(Config.DISCORD_TOKEN.val)
                .withRecommendedShardCount()
                .build();

        Object[] dispatchListeners = {
                new CommandManager(),
                new ReactionListener()
        };

        client.getDispatcher().registerListeners(dispatchListeners);
        Utils.LOG.info("Client has registered listeners successfully");

        client.login();
    }


    private static void onShutdown() {
        Utils.LOG.info("Shutting down gracefully ...");
    }
}
