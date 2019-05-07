package me.util;

import me.GalleryBot;

public enum Config {
    DISCORD_TOKEN("DISCORD_TOKEN"),
    DEV_ID("DEV_ID_STR"),
    TWITTER_TOKEN("TWITTER_TOKEN"),
    DEFAULT_PREFIX("DEFAULT_PREFIX"),
    GUILD_ID("GUILD_ID"),
    CONTEST_SUB_CH_ID("CONTEST_SUBMISSIONS_CH_ID"),
    NSFW_CON_SUB_CH_ID("NSFW_CONTEST_SUBMISSIONS_CH_ID"),
    CONTEST_VOTE_CH_ID("CONTEST_VOTING_CH_ID"),
    NSFW_CON_VOTE_CH_ID("NSW_CONTEST_VOTING_CH_ID"),
    GIGAMOD_ROLE_ID("GIGAMOD_ROLE_ID");


    public final String val;

    Config(String KEY) {
        this.val = GalleryBot.config.get(KEY);
    }
}
