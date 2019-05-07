package me.core.contest;

import sx.blah.discord.handle.obj.IMessage;

import java.time.Instant;

public class ContestSubmission {
    private String artistName;
    private IMessage message;
    private String imgUrl;
    private Instant submissionTime; // message.getTimestamp()

    private int numVotes = 0;

    public ContestSubmission(String artistName, IMessage message, String imgUrl) {
        this.artistName = artistName;
        this.message = message;
        this.imgUrl = imgUrl;
    }

    public String getArtistName() {
        return artistName;
    }

    public IMessage getMessage() {
        return message;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
