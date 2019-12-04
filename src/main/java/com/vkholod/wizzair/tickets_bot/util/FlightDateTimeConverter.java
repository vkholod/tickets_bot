package com.vkholod.wizzair.tickets_bot.util;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class FlightDateTimeConverter extends StdConverter<List<LocalDateTime>, List<String>> {

    @Override
    public List<String> convert(List<LocalDateTime> localDateTimes) {
        return localDateTimes.stream()
                .map(DateTimeFormatter.ISO_DATE_TIME::format)
                .collect(Collectors.toList());
    }
}
