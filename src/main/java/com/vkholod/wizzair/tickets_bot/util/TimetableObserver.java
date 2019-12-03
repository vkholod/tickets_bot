package com.vkholod.wizzair.tickets_bot.util;

import com.vkholod.wizzair.tickets_bot.model.Timetable;
import com.vkholod.wizzair.tickets_bot.telegram.VovaTicketsBot;

import java.io.IOException;

public class TimetableObserver {

    private VovaTicketsBot bot;

    public TimetableObserver(VovaTicketsBot bot) {
        this.bot = bot;
    }

    public void notify(Timetable newTimeTable) {
        System.out.println("Timetable changed");
        try {
            bot.sendMessage(RoundTripsUtil.buildMessage(newTimeTable));
        } catch (IOException e) {
            System.out.println("Could not send message: " + e.getMessage());
        }
    }
}
