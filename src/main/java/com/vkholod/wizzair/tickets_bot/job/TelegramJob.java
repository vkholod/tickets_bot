package com.vkholod.wizzair.tickets_bot.job;

import com.fasterxml.jackson.databind.ObjectReader;
import com.vkholod.wizzair.tickets_bot.dao.RedisStorage;
import com.vkholod.wizzair.tickets_bot.model.telegram.Result;
import com.vkholod.wizzair.tickets_bot.model.telegram.Update;
import com.vkholod.wizzair.tickets_bot.telegram.TelegramMessageProcessor;
import com.vkholod.wizzair.tickets_bot.telegram.VovaTicketsBot;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TelegramJob implements Job {

    public TelegramJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap data = jobExecutionContext.getMergedJobDataMap();

        VovaTicketsBot bot = VovaTicketsBot.class.cast(data.get("bot"));
        RedisStorage storage = RedisStorage.class.cast(data.get("storage"));
        ObjectReader reader = ObjectReader.class.cast(data.get("reader"));
        List<TelegramMessageProcessor> processors = List.class.cast(data.get("processors"));

        try {
            Update update = getUpdate(bot, storage, reader);

            processors.forEach(processor -> processor.process(update));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Update getUpdate(VovaTicketsBot bot, RedisStorage storage, ObjectReader reader) throws IOException {
        Optional<Integer> offset = storage.findTelegramOffsetInRedis();

        String value = offset.isPresent() ? bot.getUpdates(offset.get()) : bot.getUpdates(0);

        System.out.println(value);

        Update update = reader.readValue(value);

        if (!offset.isPresent()) { // removing old results to avoid to much messages for processing if offset is unavailable
            update.setResult(update.getResult().stream()
                    .filter(filterOutdated)
                    .collect(Collectors.toList())
            );
        }

        determineLatestOffset(update).ifPresent(latestOffset -> storage.saveTelegramOffsetInRedis(latestOffset + 1));

        return update;
    }

    private Predicate<Result> filterOutdated =
            result -> {
                long millis = Long.parseLong(result.getMessage().getDate());

                LocalDateTime messageTimestamp = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(millis),
                        TimeZone.getDefault().toZoneId()
                );

                Duration duration = Duration.between(messageTimestamp, LocalDateTime.now());

                return duration.getSeconds() < 5 * 60; // 5 mis
            };

    private Optional<Integer> determineLatestOffset(Update update) {
        return update.getResult().stream()
                .map(result -> Integer.parseInt(result.getUpdateId()))
                .sorted(Comparator.reverseOrder())
                .findFirst();
    }
}
