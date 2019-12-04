package com.vkholod.wizzair.tickets_bot.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vkholod.wizzair.tickets_bot.dao.RedisStorage;
import com.vkholod.wizzair.tickets_bot.job.TelegramJob;
import com.vkholod.wizzair.tickets_bot.job.TimetableCheckJob;
import com.vkholod.wizzair.tickets_bot.model.telegram.Update;
import com.vkholod.wizzair.tickets_bot.service.TimetableService;
import com.vkholod.wizzair.tickets_bot.telegram.TelegramMessageProcessor;
import com.vkholod.wizzair.tickets_bot.telegram.VovaTicketsBot;
import org.quartz.*;

import java.util.List;

import static com.vkholod.wizzair.tickets_bot.util.Const.DEFAULT_GROUP_NAME;
import static com.vkholod.wizzair.tickets_bot.util.Const.TELEGRAM_JOB_NAME;
import static com.vkholod.wizzair.tickets_bot.util.Const.TIMETABLE_CHECK_JOB_NAME;

public class QuartzUtils {

    public static JobDetail createTelegramJob(VovaTicketsBot bot, RedisStorage storage, ObjectMapper mapper, List<TelegramMessageProcessor> processors) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("bot", bot);
        jobDataMap.put("storage", storage);
        jobDataMap.put("reader", mapper.readerFor(Update.class));
        jobDataMap.put("processors", processors);

        return JobBuilder.newJob(TelegramJob.class)
                .withIdentity(TELEGRAM_JOB_NAME)
                .usingJobData(jobDataMap)
                .storeDurably(true)
                .build();
    }

    public static JobDetail createTimetableCheckJob(VovaTicketsBot bot, RedisStorage storage, TimetableService timetableService, List<TelegramMessageProcessor> processors) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("bot", bot);
        jobDataMap.put("timetableService", timetableService);
        jobDataMap.put("storage", storage);
        jobDataMap.put("processors", processors);

        return JobBuilder.newJob(TimetableCheckJob.class)
                .withIdentity(TIMETABLE_CHECK_JOB_NAME)
                .usingJobData(jobDataMap)
                .storeDurably(true)
                .build();
    }

    public static Trigger preparePermanentTrigger(String jobName, String triggerName, int interval) {
        ScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(interval)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .withIdentity(triggerName)
                .forJob(jobName)
                .startNow()
                .withSchedule(scheduleBuilder)
                .build();
    }

}
