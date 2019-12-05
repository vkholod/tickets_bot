package com.vkholod.wizzair.tickets_bot.job;

import com.vkholod.wizzair.tickets_bot.dao.RedisStorage;
import com.vkholod.wizzair.tickets_bot.model.Timetable;
import com.vkholod.wizzair.tickets_bot.model.TimetableRequestDto;
import com.vkholod.wizzair.tickets_bot.service.TimetableService;
import com.vkholod.wizzair.tickets_bot.telegram.VovaTicketsBot;
import com.vkholod.wizzair.tickets_bot.util.RoundTripsUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.io.IOException;
import java.util.Optional;

public class TimetableCheckJob implements Job {

    public TimetableCheckJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap data = jobExecutionContext.getMergedJobDataMap();

        TimetableRequestDto timetableRequestDto = TimetableRequestDto.defaultDto();

        VovaTicketsBot bot = VovaTicketsBot.class.cast(data.get("bot"));
        TimetableService timetableService = TimetableService.class.cast(data.get("timetableService"));
        RedisStorage storage = RedisStorage.class.cast(data.get("storage"));

        try {
            System.out.println("TimetableCheckJob start");
            Timetable fetchedTimetable = timetableService.getTimetable(timetableRequestDto);
            Optional<Timetable> savedTimetable = storage.findTimetableInRedis(timetableRequestDto.generateRedisKey());

            if (!savedTimetable.isPresent()) {
                storage.saveTimetableInRedis(timetableRequestDto.generateRedisKey(), fetchedTimetable);
                System.out.println("TimetableCheckJob saved timetable to Redis");
            } else if (!savedTimetable.get().equals(fetchedTimetable)) {
                System.out.println("TimetableCheckJob found diff");
                storage.saveTimetableInRedis(timetableRequestDto.generateRedisKey(), fetchedTimetable);
                bot.sendMessage(RoundTripsUtil.buildMessage(fetchedTimetable));
            } else {
                System.out.println("TimetableCheckJob no changes");
                bot.sendSilentMessage("I am alive, no changes yet");
            }
            System.out.println("TimetableCheckJob finish");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
