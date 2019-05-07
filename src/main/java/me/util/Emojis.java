package me.util;

import sx.blah.discord.handle.impl.obj.ReactionEmoji;

public enum Emojis {
    A("\uD83C\uDDE6"),
    P("\uD83C\uDDF5"),

    UP("⬆"),
    DOWN("⬇");

    private ReactionEmoji emoji;

    Emojis(String unicode) {
        emoji = ReactionEmoji.of(unicode);
    }


    public boolean equals(ReactionEmoji other) {
        return emoji.equals(other);
    }
}
