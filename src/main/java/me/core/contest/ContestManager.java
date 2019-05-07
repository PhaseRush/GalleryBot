package me.core.contest;

import com.google.gson.reflect.TypeToken;
import me.util.Utils;

import java.util.List;

public class ContestManager {

    private static Contest currentContest = Utils.GSON.fromJson(Utils.readFile("data/current_contest.json"),
            Contest.class);
    private static List<Contest> pastContests = Utils.GSON.fromJson(Utils.readFile("data/past_contests.json"),
            new TypeToken<List<Contest>>(){}.getType());


    public static void addSubmission (ContestSubmission submission) {
        currentContest.addSubmission(submission);
    }


}
