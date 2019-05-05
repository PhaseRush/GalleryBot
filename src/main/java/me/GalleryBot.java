package me;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.core.cmd.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;

public class GalleryBot {
    private final static Logger LOG = LoggerFactory.getLogger("GalleryBot");
    private final static Gson gson = new Gson();

    public static Map<String, String> launchConfig;
    public static IDiscordClient client;

    public static void main(String[] args) {
        Locale.setDefault(Locale.CANADA);
        Runtime.getRuntime().addShutdownHook(new Thread(GalleryBot::onShutdown));

        try {
            Type configType = new TypeToken<Map<String,String>>(){}.getType();
            launchConfig = gson.fromJson(Files.readString(Path.of("data/launch.json")), configType);
        } catch (IOException e) {
            LOG.error("Could not parse launch configuration map");
            e.printStackTrace();
            System.exit(2);
        }

        client = new ClientBuilder()
                .withToken(launchConfig.get("DISCORD_TOKEN"))
                .withRecommendedShardCount()
                .build();

        Object[] dispatchListeners = {new CommandManager()};

        client.getDispatcher().registerListeners(dispatchListeners);
        client.login();

        LOG.info("Client has registered listeners and logged in successfully");
    }


    private static void onShutdown() {

    }
}
