package me.commands.contest;

import me.GalleryBot;
import me.community.ProfileManager;
import me.core.cmd.Command;
import me.core.cmd.Context;
import me.core.contest.Contest;
import me.core.contest.ContestManager;
import me.core.contest.ContestSubmission;
import me.core.error.CommandStateException;
import me.core.permission.Permission;
import me.util.Config;
import me.util.DiscordUtil;
import sx.blah.discord.handle.obj.IChannel;

import java.time.format.DateTimeFormatter;

public class SubmitCmd extends Command {
    private static final IChannel CONTEST_SUB_CH = GalleryBot.client.getChannelByID(Long.valueOf(Config.CONTEST_SUB_CH_ID.val));
    private static final IChannel NSFW_CONTEST_SUB_CH = GalleryBot.client.getChannelByID(Long.valueOf(Config.NSFW_CON_SUB_CH_ID.val));

    public SubmitCmd() {
        super("submit");
    }

    @Override
    public void execute(Context context) throws CommandStateException {
        Contest contest = ContestManager.getCurrentContest();
        // notify and return if not valid channel
        if (!context.getChannel().equals(CONTEST_SUB_CH) || !context.getChannel().equals(NSFW_CONTEST_SUB_CH)) {
            context.reply("You may only submit in <#" + CONTEST_SUB_CH.getStringID() + ">\n" +
                    "NSFW submissions go in <#" + NSFW_CONTEST_SUB_CH.getStringID() + ">");
            return;
        }

        if (contest.submissionOpen()
                || DiscordUtil.getPerm(context.getMessage().getAuthor()).isAdequate(Permission.ADMIN)) {
            // first alert if they had already submitted
            if (ContestManager.getCurrentContest().getSubmissions().stream()
                    .anyMatch(sub -> sub.getArtistDiscordId().equals(context.getUser().getStringID()))) {
                context.reply("You have already submitted to this contest. It will be overridden with your new submission");
            }
            // proceed with submission
            String name = ProfileManager.getArtistNameFromUser(context.getUser())
                    .orElseGet(() -> DiscordUtil.getNickOrDefault(context.getUser(), context.getGuild()));
            try {
                ContestManager.addSubmission(new ContestSubmission(
                        name,
                        context.getMessage().getStringID(),
                        context.getMessage().getAttachments().get(0).getUrl(),
                        context.getChannel().isNSFW(),
                        context.getUser().getStringID()));

                // let them know submission has been processed
                context.reply(String.format("Your submission has been received!\nName: %s\nImage Url: <%s>",
                        name,
                        context.getMessage().getAttachments().get(0).getUrl()));
            } catch (IndexOutOfBoundsException e) {
                context.reply("You did not attach an image! Please try again.");
            }
        } else {
            context.reply(String.format("The submission period for the current contest was closed on %s.\n" +
                            "if you believe this to be an error, contact a @Gigamod and they can submit for you",
                    contest.getSubmissionEndTime().format(DateTimeFormatter.BASIC_ISO_DATE)));
        }
    }

    @Override
    public String getHelp() {
        return "!gg submit ";
    }

    @Override
    public String getDesc() {
        return "Submit artwork for the current contest";
    }
}
