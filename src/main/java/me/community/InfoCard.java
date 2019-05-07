package me.community;

import sx.blah.discord.handle.obj.IUser;

public class InfoCard {
    private String artistName, email, twitter, instagram, facebook, artstation, pixiv;
    private String picUrl;
    private String discordId;


    public InfoCard(String artistName, IUser discordUser) {
        this.artistName = artistName;
        this.discordId = discordUser.getStringID();
    }

    public String getArtistName() {
        return artistName;
    }

    public InfoCard setArtistName(String artistName) {
        this.artistName = artistName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public InfoCard setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getTwitter() {
        return twitter;
    }

    public InfoCard setTwitter(String twitter) {
        this.twitter = twitter;
        return this;
    }

    public String getInstagram() {
        return instagram;
    }

    public InfoCard setInstagram(String instagram) {
        this.instagram = instagram;
        return this;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getArtstation() {
        return artstation;
    }

    public InfoCard setArtstation(String artstation) {
        this.artstation = artstation;
        return this;
    }

    public String getPixiv() {
        return pixiv;
    }

    public InfoCard setPixiv(String pixiv) {
        this.pixiv = pixiv;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public InfoCard setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public String getDiscordId() {
        return discordId;
    }

    public InfoCard setDiscordId(String discordId) {
        this.discordId = discordId;
        return this;
    }
}
