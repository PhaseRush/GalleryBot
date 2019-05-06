package me;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.core.cmd.CommandManager;
import me.passive.ReactionListener;
import me.util.Utils;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Map;

public class GalleryBot {
    private final static Gson gson = new Gson();

    public static Map<String, String> launchConfig;
    public static IDiscordClient client;

    public static void main(String[] args) {
        Utils.LOG.info("GalleryBot starting ...");
        Locale.setDefault(Locale.CANADA);
        Runtime.getRuntime().addShutdownHook(new Thread(GalleryBot::onShutdown));

        try {
            Type configType = new TypeToken<Map<String,String>>(){}.getType();
            launchConfig = gson.fromJson(Utils.readFile("data/launch.json"), configType);
        } catch (IOException | URISyntaxException e) {
            Utils.LOG.error("Could not parse launch configuration map, shutting down");
            e.printStackTrace();
            System.exit(2);
        }

        client = new ClientBuilder()
                .withToken(launchConfig.get("DISCORD_TOKEN"))
                .withRecommendedShardCount()
                .build();

        Object[] dispatchListeners = {
                new CommandManager(),
                new ReactionListener()
        };

        client.getDispatcher().registerListeners(dispatchListeners);
        client.login();

        Utils.LOG.info("Client has registered listeners and logged in successfully");
    }


    private static void onShutdown() {
        Utils.LOG.info("Shutting down gracefully ...");
    }
}
