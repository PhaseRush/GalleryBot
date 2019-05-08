package me.community;

public class ProfileCard {
    private String discordId;
    private String url;
    private String imageUrl;

    public ProfileCard(String discordId, String url, String imageUrl) {
        this.discordId = discordId;
        this.url = url;
        this.imageUrl = imageUrl;
    }
}
