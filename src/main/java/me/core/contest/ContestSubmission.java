package me.core.contest;

import java.time.Instant;

public class ContestSubmission {
    private String artistName;
    private String messageId;
    private String imgUrl;
    private Instant submissionTime; // message.getTimestamp()

    private int numVotes = 0;

    public ContestSubmission(String artistName, String messageId, String imgUrl) {
        this.artistName = artistName;
        this.messageId = messageId;
        this.imgUrl = imgUrl;
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
