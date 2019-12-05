package com.vkholod.wizzair.tickets_bot.util;

import java.time.format.DateTimeFormatter;

public class Const {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final DateTimeFormatter DATE_TIME_HR_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm:ss");

    public static final String TELEGRAM_JOB_NAME = "telegramJob";
    public static final String TELEGRAM_TRIGGER_NAME = "telegramTrigger";
    public static final String TIMETABLE_CHECK_JOB_NAME = "timeTableCheckJob";
    public static final String TIMETABLE_CHECK_TRIGGER_NAME = "timeTableCheckTrigger";
    public static final String DEFAULT_GROUP_NAME = "defaultGroup";


}
