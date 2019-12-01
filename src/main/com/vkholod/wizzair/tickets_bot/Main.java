package com.vkholod.wizzair.tickets_bot;

import com.vkholod.wizzair.tickets_bot.model.Flight;
import com.vkholod.wizzair.tickets_bot.model.FlightPair;
import com.vkholod.wizzair.tickets_bot.model.Timetable;
import com.vkholod.wizzair.tickets_bot.model.TimetableRequestDto;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final String SUCEAVA = "SCV";
    private static final String ROME = "CIA";

    public static void main(String[] args) throws InterruptedException, IOException {
        LocalDate from = LocalDate.of(2020, 5, 11);
        LocalDate to = LocalDate.of(2020, 5, 31);

        Timetable timetable = get(from, to);

        List<Flight> outboundFlights = timetable.getOutboundFlights();
        outboundFlights.sort(Comparator.comparing(Flight::departureDateTime));

        List<Flight> returnFlights = timetable.getReturnFlights();
        returnFlights.sort(Comparator.comparing(Flight::departureDateTime));

        for (Flight outboundFlight : outboundFlights) {
            returnFlights.stream()
                    .filter(returnFlight -> returnFlight.isEqualOrAfter(outboundFlight))
                    .map(returnFlight -> new FlightPair(outboundFlight, returnFlight))
                    .collect(Collectors.toList());
        }

        outboundFlights.stream()
                .flatMap(outboundFlight ->
                        returnFlights.stream()
                            .filter(returnFlight -> returnFlight.isEqualOrAfter(outboundFlight))
                            .map(returnFlight -> new FlightPair(outboundFlight, returnFlight))
                            .collect(Collectors.toList()).stream()
                ).sorted(Comparator.comparing(FlightPair::totalPrice))
                .forEach(flightPair -> System.out.println(flightPair + "\n"));
    }

    private static Timetable get(LocalDate from, LocalDate to) throws IOException {
        WizClient client = new WizClient();

        TimetableRequestDto timetableRequestDto = TimetableRequestDto.create(
                SUCEAVA, ROME,
                from, to,
                2
        );

        return client.getTimetable(timetableRequestDto);
    }

}
