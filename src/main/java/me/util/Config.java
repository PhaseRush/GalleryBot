package me.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

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
    GIGAMOD_ROLE_ID("GIGAMOD_ROLE_ID"),
    GITHUB_URL("GITHUB_URL");


    private static class ConfigMap{
        private static final Type configType = new TypeToken<Map<String,String>>(){}.getType();
        private static final Map<String, String> config = new Gson().fromJson(Utils.readFile("data/launch.json"), configType);
    }

    public String val;

    Config(String key) {
        this.val = ConfigMap.config.get(key);
    }
}
