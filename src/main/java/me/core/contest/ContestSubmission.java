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

    private int numVotes;
    private boolean isNSFW;

    public ContestSubmission(String artistName, String messageId, String imgUrl, boolean isNSFW) {
        this.artistName = artistName;
        this.messageId = messageId;
        this.imgUrl = imgUrl;
        this.isNSFW = isNSFW;
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
}
