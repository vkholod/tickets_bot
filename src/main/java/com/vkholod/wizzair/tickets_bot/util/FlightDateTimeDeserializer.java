package com.vkholod.wizzair.tickets_bot.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.vkholod.wizzair.tickets_bot.util.Const.DATE_FORMATTER;
import static com.vkholod.wizzair.tickets_bot.util.Const.DATE_TIME_FORMATTER;

public class FlightDateTimeDeserializer extends StdDeserializer<List<LocalDateTime>> {

    protected FlightDateTimeDeserializer() {
        super(List.class);
    }

    @Override
    public List<LocalDateTime> deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        List<LocalDateTime> flightDateTimes = new ArrayList<>();

        for (Object value : parser.readValueAs(List.class)) {
            String rawDateTime = String.valueOf(value);

            flightDateTimes.add(LocalDateTime.parse(rawDateTime, DateTimeFormatter.ISO_DATE_TIME));
        }

        return flightDateTimes;
    }
}
