package me.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Utils {
    private static final Gson GSON = new Gson();

    private static Type prefixMapType = new TypeToken<HashMap<String, String>>() {}.getType();
    private static HashMap<String, String> prefixMap;

    static {
        try {
            prefixMap = GSON.fromJson(
                    Files.readString(Path.of("data/prefix_map.json")),
                    prefixMapType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper functions to make certain aspects of the bot easier to use.
    public static void send(IChannel channel, String message) {
        RequestBuffer.request(() -> {
            try {
                channel.sendMessage(message);
            } catch (DiscordException e) {
                System.err.println("Message could not be sent with error: ");
                e.printStackTrace();
            } catch (MissingPermissionsException e) {
                send(channel, "Missing Permissions: " + channel.getName() + " Msg: " + message);
            }
        });
    }


    public static String getPrefix(MessageReceivedEvent event) { // cant use IID b/c need to preserve channel > guild
        if (event.getChannel().isPrivate()) return prefixMap.get("DEFAULT_PREFIX");
        return prefixMap.getOrDefault(event.getChannel().getStringID(),
                prefixMap.getOrDefault(event.getGuild().getStringID(),
                        prefixMap.get("DEFAULT_PREFIX")));
    }
}
