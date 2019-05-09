package me.core.contest;

import java.time.Instant;

/**
 * Each artist allowed exactly one submission, with exactly one image
 */
public class ContestSubmission {
    private String artistName;
    private String messageId;
    private String imgUrl;
    private Instant submissionTime; // message.getTimestamp()
    private String artistDiscordId;

    private int numVotes;
    private boolean isNSFW;

    public ContestSubmission(String artistName, String messageId, String imgUrl, boolean isNSFW, String artistDiscordId) {
        this.artistName = artistName;
        this.messageId = messageId;
        this.imgUrl = imgUrl;
        this.isNSFW = isNSFW;
        this.artistDiscordId = artistDiscordId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Instant getSubmissionTime() {
        return submissionTime;
    }

    public int getNumVotes() {
        return numVotes;
    }

    public String getArtistDiscordId() {
        return artistDiscordId;
    }
}
