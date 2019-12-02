package com.vkholod.wizzair.tickets_bot.resources;

import com.vkholod.wizzair.tickets_bot.model.RoundTrip;
import com.vkholod.wizzair.tickets_bot.model.Timetable;
import com.vkholod.wizzair.tickets_bot.model.TimetableRequestDto;
import com.vkholod.wizzair.tickets_bot.service.RoundTripsService;
import com.vkholod.wizzair.tickets_bot.service.TimetableService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Path("/roundTrips")
@Produces(MediaType.APPLICATION_JSON)
public class RoundTripResource {

    private RoundTripsService roundTripsService;
    private TimetableService timetableService;

    public RoundTripResource(RoundTripsService roundTripsService, TimetableService timetableService) {
        this.roundTripsService = roundTripsService;
        this.timetableService = timetableService;
    }

    @GET
    @Produces("application/json")
    public List<RoundTrip> roundTrips() throws IOException {
        TimetableRequestDto timetableRequestDto = TimetableRequestDto.create(
                "SCV", "CIA",
                LocalDate.of(2020, 5, 11), LocalDate.of(2020, 5, 31),
                2
        );

        Timetable timetable = timetableService.getTimetable(timetableRequestDto);

        return roundTripsService.getRoundTrips(timetable);
    }

}
