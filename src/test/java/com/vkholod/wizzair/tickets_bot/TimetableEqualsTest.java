package com.vkholod.wizzair.tickets_bot;

import com.vkholod.wizzair.tickets_bot.model.Flight;
import com.vkholod.wizzair.tickets_bot.model.Price;
import com.vkholod.wizzair.tickets_bot.model.Timetable;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class TimetableEqualsTest {

    private Timetable timetable1;
    private Timetable timetable2;

    @Test
    public void testEqualTimetables() {
        assertEquals(timetable1, timetable2);
    }

    @Test
    public void testDiffOutboundPrice() {
        Flight outboundFlight = timetable2.getOutboundFlights().stream().findAny().get();

        outboundFlight.getPrice().setAmount(outboundFlight.getPrice().getAmount().add(BigDecimal.ONE));

        assertNotEquals(timetable1, timetable2);
    }

    @Test
    public void testDiffReturnPrice() {
        Flight returnFlight = timetable2.getReturnFlights().stream().findAny().get();

        returnFlight.getPrice().setAmount(returnFlight.getPrice().getAmount().add(BigDecimal.ONE));

        assertNotEquals(timetable1, timetable2);
    }

    @Test
    public void testDiffOutboundDepartureDates() {
        Flight outBoundFlight = timetable2.getOutboundFlights().stream().findAny().get();

        outBoundFlight.setDepartureDates(
                outBoundFlight.getDepartureDates().stream()
                        .map(departureDate -> departureDate.plusHours(1))
                        .collect(Collectors.toList())
        );

        assertNotEquals(timetable1, timetable2);
    }

    @Before
    public void setUp() {
        LocalDateTime now = LocalDateTime.now();

        Flight outBoundFlight1 = new Flight(
                "ABC", "XYZ",
                now.toLocalDate(), now.toLocalDate().plusDays(1),
                new Price(BigDecimal.TEN, "USD"),
                List.of(now.plusHours(5))
        );

        Flight outBoundFlight2 = new Flight(
                "ABC", "XYZ",
                now.toLocalDate(), now.toLocalDate().plusDays(1),
                new Price(BigDecimal.TEN, "USD"),
                List.of(now.plusHours(5))
        );

        Flight returnFlight1 = new Flight(
                "XYZ", "ABC",
                now.toLocalDate(), now.toLocalDate().plusDays(1),
                new Price(BigDecimal.TEN, "USD"),
                List.of(now.plusHours(10))
        );

        Flight returnFlight2 = new Flight(
                "XYZ", "ABC",
                now.toLocalDate(), now.toLocalDate().plusDays(1),
                new Price(BigDecimal.TEN, "USD"),
                List.of(now.plusHours(10))
        );

         timetable1 = new Timetable(List.of(outBoundFlight1), List.of(returnFlight1));
         timetable2 = new Timetable(List.of(outBoundFlight2), List.of(returnFlight2));
    }
}
