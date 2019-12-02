package com.vkholod.wizzair.tickets_bot.util;

import com.vkholod.wizzair.tickets_bot.model.Timetable;

public class TimetableObserver {

    public TimetableObserver() {
    }

    public void notify(Timetable newTimeTable) {
        System.out.println("Timetable changed");
        // TODO: send notification to Telegram
    }
}
