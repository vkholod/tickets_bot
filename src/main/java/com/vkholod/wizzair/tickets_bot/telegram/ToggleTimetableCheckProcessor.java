package com.vkholod.wizzair.tickets_bot.telegram;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.io.IOException;

import static com.vkholod.wizzair.tickets_bot.util.Const.TIMETABLE_CHECK_JOB_NAME;

public class ToggleTimetableCheckProcessor extends TelegramMessageProcessor {

    private Scheduler scheduler;

    public ToggleTimetableCheckProcessor(VovaTicketsBot bot, Scheduler scheduler) {
        super(bot);
        this.scheduler = scheduler;
    }

    @Override
    protected void internalProcess(String message) {
        try {
            if (StringUtils.contains(message, "resume")) {
                scheduler.resumeJob(JobKey.jobKey(TIMETABLE_CHECK_JOB_NAME));

                bot.sendMessage("Timetable checker resumed");
            } else if (StringUtils.contains(message, "pause")) {
                scheduler.pauseJob(JobKey.jobKey(TIMETABLE_CHECK_JOB_NAME));

                bot.sendMessage("Timetable checker paused");
            } else {
                bot.sendMessage("Could not recognize command, use '/toggle resume' or '/toggle pause'");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (SchedulerException e) {
            try {
                bot.sendMessage("Could not start job");
            } catch (IOException e1) {
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    protected String keyWord() {
        return "/toggle";
    }
}
