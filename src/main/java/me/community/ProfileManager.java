package me.community;

import com.google.gson.reflect.TypeToken;
import me.util.Utils;
import sx.blah.discord.handle.obj.IUser;

import java.util.Optional;
import java.util.Set;

public class ProfileManager {
    private static Set<ProfileCard> profileCards = Utils.GSON.fromJson(Utils.readFile("data/profiles.json"),
            new TypeToken<Set<ProfileCard>>(){}.getType());


    public static Optional<String> getArtistNameFromUser(IUser user) {
        return profileCards.stream()
                .filter(card -> card.getDiscordId().equals(user.getStringID()))
                .map(ProfileCard::getArtistName)
                .findAny();
    }


}
