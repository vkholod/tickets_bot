package com.vkholod.wizzair.tickets_bot.util;

import com.vkholod.wizzair.tickets_bot.model.Flight;
import com.vkholod.wizzair.tickets_bot.model.RoundTrip;
import com.vkholod.wizzair.tickets_bot.model.Timetable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RoundTripsUtil {


    public static List<RoundTrip> buildRoundTrips(Timetable timetable) {
        List<Flight> outboundFlights = timetable.getOutboundFlights();
        outboundFlights.sort(Comparator.comparing(Flight::departureDateTime));

        List<Flight> returnFlights = timetable.getReturnFlights();
        returnFlights.sort(Comparator.comparing(Flight::departureDateTime));

        for (Flight outboundFlight : outboundFlights) {
            returnFlights.stream()
                    .filter(returnFlight -> returnFlight.isEqualOrAfter(outboundFlight))
                    .map(returnFlight -> new RoundTrip(outboundFlight, returnFlight))
                    .collect(Collectors.toList());
        }

        return outboundFlights.stream()
                .flatMap(outboundFlight ->
                        returnFlights.stream()
                                .filter(returnFlight -> returnFlight.isEqualOrAfter(outboundFlight))
                                .map(returnFlight -> new RoundTrip(outboundFlight, returnFlight))
                                .collect(Collectors.toList()).stream()
                ).sorted(Comparator.comparing(RoundTrip::totalPrice))
                .collect(Collectors.toList());
    }

    public static String buildMessage(Timetable timetable) {
        StringBuilder messageBuilder = new StringBuilder("Timetable changed\n");

        String roundTripsMessage = buildRoundTrips(timetable).stream()
                .map(RoundTrip::toString)
                .collect(Collectors.joining("\n----------\n"));

        messageBuilder.append(roundTripsMessage);

        return messageBuilder.toString();
    }

}