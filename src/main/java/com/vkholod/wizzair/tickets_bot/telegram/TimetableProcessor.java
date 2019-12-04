package com.vkholod.wizzair.tickets_bot.telegram;

import com.vkholod.wizzair.tickets_bot.dao.RedisStorage;
import com.vkholod.wizzair.tickets_bot.model.Timetable;
import com.vkholod.wizzair.tickets_bot.model.TimetableRequestDto;
import com.vkholod.wizzair.tickets_bot.service.TimetableService;
import com.vkholod.wizzair.tickets_bot.util.RoundTripsUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Optional;

public class TimetableProcessor extends TelegramMessageProcessor {

    private TimetableService service;
    private RedisStorage redisStorage;

    public TimetableProcessor(VovaTicketsBot bot, TimetableService service, RedisStorage redisStorage) {
        super(bot);
        this.service = service;
        this.redisStorage = redisStorage;
    }

    @Override
    public void internalProcess(String message) {
        TimetableRequestDto dto = TimetableRequestDto.defaultDto();

        Timetable timetable = null;
        try {
            if (StringUtils.containsIgnoreCase(message, "cache")) {
                Optional<Timetable> cachedTimetable = redisStorage.findTimetableInRedis(dto.generateRedisKey());
                if (cachedTimetable.isPresent()) {
                    timetable = cachedTimetable.get();
                } else {
                    bot.sendMessage("Timetable cache is empty");
                }
            } else {
                timetable = service.getTimetable(dto);
            }

            bot.sendMessage(RoundTripsUtil.buildMessage(timetable));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected String keyWord() {
        return "/tt";
    }
}
