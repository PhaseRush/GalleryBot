package me.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.core.cmd.Command;
import me.core.cmd.CommandManager;
import me.core.cmd.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

public class Utils {
    public static final Gson GSON = new Gson();
    public final static Logger LOG = LoggerFactory.getLogger("GalleryBot");

    private static HashMap<String, String> prefixMap = GSON.fromJson(readFile("data/prefix_map.json"),
            new TypeToken<HashMap<String, String>>() {}.getType());

    public static HashMap<String, String> getPrefixMap() {
        return prefixMap;
    }

    // Helper functions to make certain aspects of the bot easier to use.
    public static void send(IChannel channel, String message) {
        RequestBuffer.request(() -> {
            try {
                return channel.sendMessage(message);
            } catch (DiscordException | MissingPermissionsException e) {
                LOG.error("Message could not be sent with error:\n" + e.getMessage());
                return null;
            }
        });
    }

    public static IMessage sendGet(IChannel channel, String message) {
        return RequestBuffer.request(() -> {
            try {
                return channel.sendMessage(message);
            } catch (DiscordException | MissingPermissionsException e) {
                LOG.error("Message could not be sent with error:\n" + e.getMessage());
                return null;
            }
        }).get();
    }

    public static String readFile(String path) {
        try {
            return Files.lines(Paths.get(System.getProperty("user.dir") + "/" + path)).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            LOG.error(String.format("encountered error while trying to read %s : \n%s", path, e.getMessage()));
        }
        return null;
    }

    public static int levenDist(String s1, String s2) {
        if (s1 == null) {
            throw new NullPointerException("s1 must not be null");
        } else if (s2 == null) {
            throw new NullPointerException("s2 must not be null");
        } else if (s1.equals(s2)) {
            return 0;
        } else if (s1.length() == 0) {
            return s2.length();
        } else if (s2.length() == 0) {
            return s1.length();
        } else {
            int[] v0 = new int[s2.length() + 1];
            int[] v1 = new int[s2.length() + 1];

            int i;
            for(i = 0; i < v0.length; v0[i] = i++) {
            }

            for(i = 0; i < s1.length(); ++i) {
                v1[0] = i + 1;

                for(int j = 0; j < s2.length(); ++j) {
                    int cost = 1;
                    if (s1.charAt(i) == s2.charAt(j)) {
                        cost = 0;
                    }

                    v1[j + 1] = Math.min(v1[j] + 1, Math.min(v0[j + 1] + 1, v0[j] + cost));
                }

                int[] vtemp = v0;
                v0 = v1;
                v1 = vtemp;
            }

            return v0[s2.length()];
        }
    }

    public static boolean isDev(Context context) {
        return context.getMessage().getAuthor().getStringID().equals(Config.DEV_ID.val);
    }

    public static Optional<Command> cmdSpellCorrect(String inputStr) {
        return CommandManager.commandMap.entrySet().stream()
                .filter(e -> e.getValue().canAutoCorrect())
                .filter(e -> Math.abs(e.getKey().length() - inputStr.length()) < 2)
                .map(e -> new Pair<>(e.getKey(), levenDist(e.getKey(), inputStr)))
                .min(Comparator.comparingDouble(Pair::getValue))
                .filter(p -> p.getValue() < 2)
                .map(Pair::getKey)

                // this part is for checking the blacklist
                .map(name -> new Pair<>(name, CommandManager.commandMap.get(name)))
                .filter(pair -> !pair.getValue().autoCorrectBlackList().contains(inputStr))
                .flatMap(pair -> Optional.of(pair.getValue())); // flatmap to ensure not nested Op<Op<Cmd>>
    }

}
