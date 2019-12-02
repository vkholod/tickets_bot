package com.vkholod.wizzair.tickets_bot.service;

import com.vkholod.wizzair.tickets_bot.model.Flight;
import com.vkholod.wizzair.tickets_bot.model.RoundTrip;
import com.vkholod.wizzair.tickets_bot.model.Timetable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RoundTripsService {


    public List<RoundTrip> getRoundTrips(Timetable timetable) {
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

}
