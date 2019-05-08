package me.core.contest;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Contest {
    private int contestNumber;
    private String contestId = UUID.randomUUID().toString(); // not sure if needed
    private List<ContestSubmission> submissions;

    // time intervals : examples for month of June 2019
    private LocalDate submissionStartTime;      // beginning of month           June 1  00:00
    private LocalDate submissionEndTime;        // first moment of next month   July 1  00:00
    private LocalDate votingStartTime;          // first day of next month      July 1  00:00
    private LocalDate votingEndTime;            // end of 3rd day of next month July 3  00:00

    private LocalDate themeSubmissionStartTime; // 6 days before end of month   June 25 00:00
    private LocalDate themeSubmissionEndTime;   // 3 days before end of month   June 28 00:00
    private LocalDate themeVotingStartTime;     // 3 days before end of month   June 28 00:00
    private LocalDate themeVotingEndTime;       // last day of month            June 30 00:00

    // variables that change during or after contest:
    private String winnerId; // announced right when voting ends
    private boolean isFinished;

    public Contest(int contestNumber) {
        this(contestNumber, Collections.emptyList());
    }

    // just ensure enough delay to use right day
    public Contest(int contestNumber, List<ContestSubmission> submissions) {
        this(contestNumber, submissions,
                LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()),// beginning of month
                LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth()),// end of month
                LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth()),// beginning of next month
                LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth()).plus(3, ChronoUnit.DAYS),// end of 3rd day of next month

                LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).minusDays(6),// 6 days before end of month
                LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).minusDays(4),// 4 days before end of month
                LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).minusDays(3),// 3 days before end of month
                LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));// last day of month
    }

    public Contest(int contestNumber, List<ContestSubmission> submissions,
                   LocalDate submissionStartTime, LocalDate submissionEndTime, LocalDate votingStartTime, LocalDate votingEndTime,
                   LocalDate themeSubmissionStartTime, LocalDate themeSubmissionEndTime, LocalDate themeVotingStartTime, LocalDate themeVotingEndTime) {
        this.contestNumber = contestNumber;
        this.submissions = submissions;
        this.submissionStartTime = submissionStartTime;
        this.submissionEndTime = submissionEndTime;
        this.votingStartTime = votingStartTime;
        this.votingEndTime = votingEndTime;

        this.themeSubmissionStartTime = submissionStartTime;
        this.themeSubmissionEndTime = themeSubmissionEndTime;
        this.themeVotingStartTime = themeVotingStartTime;
        this.themeVotingEndTime = themeVotingEndTime;
    }

    public void addSubmission(ContestSubmission submission) {
        submissions.add(submission);
    }

    public void setWinnerId(String id) {
        this.winnerId = id;
    }
}
