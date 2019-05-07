package me;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.core.cmd.CommandManager;
import me.passive.ReactionListener;
import me.util.Utils;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;

public class GalleryBot {
    private final static Gson gson = new Gson();

    private static Type configType = new TypeToken<Map<String,String>>(){}.getType();
    public static final Map<String, String> config = gson.fromJson(Utils.readFile("data/launch.json"), configType);
    public static IDiscordClient client;

    public static final String GUILD_ID = "351908935017562113";

    public static void main(String[] args) {
        Utils.LOG.info("GalleryBot starting ...");
        Locale.setDefault(Locale.CANADA);
        Runtime.getRuntime().addShutdownHook(new Thread(GalleryBot::onShutdown));

        client = new ClientBuilder()
                .withToken(config.get("DISCORD_TOKEN"))
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
