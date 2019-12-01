package com.vkholod.wizzair.tickets_bot.util;

import com.vkholod.wizzair.tickets_bot.model.Flight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class Const {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("YYYY-MM-dd");

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("YYYY-MM-dd");

    public static final DateTimeFormatter DATE_HR_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/YY");
    public static final DateTimeFormatter DATE_TIME_HR_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/YY hh:mm");

    public static final Comparator<Flight> FLIGHT_SORTER_BY_DEPARTURE_DATE = (flight1, flight2) -> {
        LocalDateTime flight1Departure = flight1.getDepartureDates().stream()
                .sorted(Comparator.naturalOrder()).findFirst().get();

        LocalDateTime flight2Departure = flight2.getDepartureDates().stream()
                .sorted(Comparator.naturalOrder()).findFirst().get();

        return flight1Departure.compareTo(flight2Departure);
    };

}
