package com.vkholod.wizzair.tickets_bot.telegram;

import com.vkholod.wizzair.tickets_bot.util.Const;
import com.vkholod.wizzair.tickets_bot.util.DateTimeUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StatusProcessor extends TelegramMessageProcessor {

    private Scheduler scheduler;

    public StatusProcessor(VovaTicketsBot bot, Scheduler scheduler) {
        super(bot);
        this.scheduler = scheduler;
    }

    @Override
    public void internalProcess(String message) {
        try {

            List<String> jobsStatuses = new ArrayList<>();

            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.anyGroup())) {
                String triggers = scheduler.getTriggersOfJob(jobKey).stream()
                        .filter(trigger -> Objects.nonNull(trigger.getPreviousFireTime()))
                        .map(trigger -> {
                            LocalDateTime prevFireTime = DateTimeUtils.convert(trigger.getPreviousFireTime());
                            LocalDateTime nextFireTime = DateTimeUtils.convert(trigger.getNextFireTime());
                            long seconds = Duration.between(prevFireTime, nextFireTime).getSeconds();
                            return String.format("%s - %s (%s)",
                                    Const.DATE_TIME_HR_FORMATTER.format(prevFireTime),
                                    Const.DATE_TIME_HR_FORMATTER.format(nextFireTime),
                                    DateTimeUtils.timeConversion(seconds)
                            );
                        }).collect(Collectors.joining(", "));


                jobsStatuses.add(String.format("\t%s %s", jobKey.getName(), triggers));
            }


            bot.sendMessage("I am alive! Executing jobs:\n" + jobsStatuses.stream().collect(Collectors.joining("\n")));
        } catch (IOException | SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String keyWord() {
        return "/status";
    }
}
