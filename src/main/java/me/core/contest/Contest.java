package me.core.contest;

import sx.blah.discord.handle.obj.IUser;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class Contest {
    private int contestNumber;
    private String contestId = UUID.randomUUID().toString(); // not sure if needed
    private Set<ContestSubmission> submissions;

    // time intervals
    private LocalDate submissionStartTime; // beginning of month
    private LocalDate submissionEndTime; // end of month
    private LocalDate votingStartTime; // beginning of next month
    private LocalDate votingEndTime; // end of 3rd day of next month

    // variables that change during or after contest:
    private IUser winner; // announced right when voting ends
    private boolean isFinished;

    public Contest(int contestNumber) {
        this(contestNumber, Collections.emptySet());
    }

    // just ensure enough delay to use right day
    public Contest(int contestNumber, Set<ContestSubmission> submissions) {
        this(contestNumber, submissions,
                LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()),
                LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth()),
                LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth()),
                LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth()).plus(3, ChronoUnit.DAYS));
    }

    public Contest(int contestNumber, Set<ContestSubmission> submissions, LocalDate submissionStartTime, LocalDate submissionEndTime, LocalDate votingStartTime, LocalDate votingEndTime) {
        this.contestNumber = contestNumber;
        this.submissions = submissions;
        this.submissionStartTime = submissionStartTime;
        this.submissionEndTime = submissionEndTime;
        this.votingStartTime = votingStartTime;
        this.votingEndTime = votingEndTime;
    }

    public void addSubmission(ContestSubmission submission) {
        submissions.add(submission);
    }
}
