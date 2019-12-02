package com.vkholod.wizzair.tickets_bot.service;

import com.vkholod.wizzair.tickets_bot.dao.RedisStorage;
import com.vkholod.wizzair.tickets_bot.dao.WizzairTimetableClient;
import com.vkholod.wizzair.tickets_bot.model.Timetable;
import com.vkholod.wizzair.tickets_bot.model.TimetableRequestDto;
import com.vkholod.wizzair.tickets_bot.util.TimetableObserver;

import java.io.IOException;

public class TimetableService {

    private WizzairTimetableClient client;
    private RedisStorage storage;
    private TimetableObserver timetableObserver;

    public TimetableService(WizzairTimetableClient client, RedisStorage storage, TimetableObserver timetableObserver) {
        this.client = client;
        this.storage = storage;
        this.timetableObserver = timetableObserver;
    }

    public Timetable getTimetable(TimetableRequestDto timetableRequestDto) throws IOException {
        Timetable timetable = client.fetchTimetable(timetableRequestDto);

        checkIfTimeTableChanged(timetableRequestDto.generateRedisKey(), timetable);

        return timetable;
    }

    private void checkIfTimeTableChanged(String redisKey, Timetable fetchedTimetable) {
        Timetable savedTimetable = storage.findTimetableInRedis(redisKey);

        if (!savedTimetable.equals(fetchedTimetable)) {
            storage.saveTimetableInRedis(redisKey, fetchedTimetable);
            timetableObserver.notify(fetchedTimetable);
        }

    }
}
