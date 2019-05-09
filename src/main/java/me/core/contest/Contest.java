package me.core.contest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Contest {
    private String contestId = UUID.randomUUID().toString(); // not sure if needed
    private List<ContestSubmission> submissions;

    private Month contestMonth;     // meta info
    private String theme;           // meta info

    // time intervals : examples for month of June 2019
    private LocalDateTime submissionStartTime;      // beginning of month           June 1  00:00
    private LocalDateTime submissionEndTime;        // first moment of next month   July 1  00:00
    private LocalDateTime votingStartTime;          // first day of next month      July 1  00:00
    private LocalDateTime votingEndTime;            // end of 3rd day of next month July 3  00:00

    private LocalDateTime themeSubmissionStartTime; // 6 days before end of month   June 25 00:00
    private LocalDateTime themeSubmissionEndTime;   // 3 days before end of month   June 28 00:00
    private LocalDateTime themeVotingStartTime;     // 3 days before end of month   June 28 00:00
    private LocalDateTime themeVotingEndTime;       // last day of month            June 30 00:00


    // variables that change during or after contest:
    private String winnerId; // announced right when voting ends
    private boolean isFinished;

    public Contest(String theme) {
        this(theme, Collections.emptyList());
    }

    // just ensure enough delay to use right day
    public Contest(String theme, List<ContestSubmission> submissions) {
        this(theme, submissions,
                LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()),// beginning of month
                LocalDateTime.now().with(TemporalAdjusters.firstDayOfNextMonth()),// end of month
                LocalDateTime.now().with(TemporalAdjusters.firstDayOfNextMonth()),// beginning of next month
                LocalDateTime.now().with(TemporalAdjusters.firstDayOfNextMonth()).plus(3, ChronoUnit.DAYS),// end of 3rd day of next month

                LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).minusDays(6),// 6 days before end of month
                LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).minusDays(4),// 4 days before end of month
                LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).minusDays(3),// 3 days before end of month
                LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()));// last day of month
    }

    public Contest(String theme, List<ContestSubmission> submissions,
                   LocalDateTime submissionStartTime, LocalDateTime submissionEndTime, LocalDateTime votingStartTime, LocalDateTime votingEndTime,
                   LocalDateTime themeSubmissionStartTime, LocalDateTime themeSubmissionEndTime, LocalDateTime themeVotingStartTime, LocalDateTime themeVotingEndTime) {
        this.theme = theme;
        this.submissions = submissions;
        this.submissionStartTime = submissionStartTime;
        this.submissionEndTime = submissionEndTime;
        this.votingStartTime = votingStartTime;
        this.votingEndTime = votingEndTime;

        this.themeSubmissionStartTime = themeSubmissionStartTime;
        this.themeSubmissionEndTime = themeSubmissionEndTime;
        this.themeVotingStartTime = themeVotingStartTime;
        this.themeVotingEndTime = themeVotingEndTime;

        contestMonth = LocalDate.now().getMonth(); // assuming init current month
    }

    /**
     * removes previous submission if contained
     * @param submission the submission to add
     */
    public void addSubmission(ContestSubmission submission) {
        submissions.stream() // remove previous one if present
                .filter(sub -> sub.getArtistDiscordId().equals(submission.getArtistDiscordId()))
                .findAny()
                .ifPresent(sub -> submissions.remove(sub));

        submissions.add(submission);
    }

    public void setWinnerId(String id) {
        this.winnerId = id;
    }

    // validators

    /**
     * @return true IFF the period is open
     */
    public boolean submissionOpen() {
        return LocalDateTime.now().isAfter(submissionStartTime) && LocalDateTime.now().isBefore(submissionEndTime);
    }
    public boolean votingOpen() {
        return LocalDateTime.now().isAfter(votingStartTime) && LocalDateTime.now().isBefore(votingEndTime);
    }
    public boolean themeOpen() {
        return LocalDateTime.now().isAfter(themeSubmissionStartTime) && LocalDateTime.now().isBefore(themeSubmissionEndTime);
    }


    // Getters
    public String getTheme() {
        return theme;
    }

    public String getContestId() {
        return contestId;
    }

    public List<ContestSubmission> getSubmissions() {
        return submissions;
    }

    public LocalDateTime getSubmissionStartTime() {
        return submissionStartTime;
    }

    public LocalDateTime getSubmissionEndTime() {
        return submissionEndTime;
    }

    public LocalDateTime getVotingStartTime() {
        return votingStartTime;
    }

    public LocalDateTime getVotingEndTime() {
        return votingEndTime;
    }

    public LocalDateTime getThemeSubmissionStartTime() {
        return themeSubmissionStartTime;
    }

    public LocalDateTime getThemeSubmissionEndTime() {
        return themeSubmissionEndTime;
    }

    public LocalDateTime getThemeVotingStartTime() {
        return themeVotingStartTime;
    }

    public LocalDateTime getThemeVotingEndTime() {
        return themeVotingEndTime;
    }

    public String getWinnerId() {
        return winnerId;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
