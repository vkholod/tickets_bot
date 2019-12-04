package com.vkholod.wizzair.tickets_bot.service;

import com.vkholod.wizzair.tickets_bot.dao.WizzairTimetableClient;
import com.vkholod.wizzair.tickets_bot.model.Timetable;
import com.vkholod.wizzair.tickets_bot.model.TimetableRequestDto;

import java.io.IOException;

public class TimetableService {

    private WizzairTimetableClient client;

    public TimetableService(WizzairTimetableClient client) {
        this.client = client;
    }

    public Timetable getTimetable(TimetableRequestDto timetableRequestDto) throws IOException {
        Timetable timetable = client.fetchTimetable(timetableRequestDto);

        return timetable;
    }
}
