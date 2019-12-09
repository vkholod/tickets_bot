package com.vkholod.wizzair.tickets_bot.resources;

import com.vkholod.wizzair.tickets_bot.model.RoundTrip;
import com.vkholod.wizzair.tickets_bot.model.Timetable;
import com.vkholod.wizzair.tickets_bot.model.TimetableRequestDto;
import com.vkholod.wizzair.tickets_bot.service.TimetableService;
import com.vkholod.wizzair.tickets_bot.util.RoundTripsUtil;
import com.vkholod.wizzair.tickets_bot.view.RoundTripsView;
import io.dropwizard.jersey.jsr310.LocalDateParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class RoundTripResource {

    private TimetableService timetableService;

    public RoundTripResource(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response ping() {
        return Response.ok("OK").build();
    }

    @GET
    @Path("roundTrips")
    @Produces(MediaType.TEXT_HTML)
    public RoundTripsView roundTrips(
            @QueryParam("departure") String departure,
            @QueryParam("arrival") String arrival,
            @QueryParam("from") LocalDateParam from,
            @QueryParam("to") LocalDateParam to,
            @QueryParam("adults") int adults,
            @DefaultValue("0") @QueryParam("minStay") int minStay,
            @DefaultValue("31") @QueryParam("maxStay") int maxStay,
            @DefaultValue("price") @QueryParam("sort") String sort
    ) throws IOException {
        TimetableRequestDto dto = TimetableRequestDto.create(departure, arrival, from.get(), to.get(), adults);

        Timetable timetable = timetableService.getTimetable(dto);

        List<RoundTrip> roundTrips = RoundTripsUtil.buildRoundTrips(timetable).stream()
                .filter(roundTrip -> roundTrip.getDuration() >= minStay && roundTrip.getDuration() <= maxStay)
                .collect(Collectors.toList());

        switch (sort) {
            case "price": {
                roundTrips.sort(RoundTripsUtil.priceSort);
                break;
            }
            case "outboundDate": {
                roundTrips.sort(RoundTripsUtil.outboundDateSort);
                break;
            }
            case "returnDate": {
                roundTrips.sort(RoundTripsUtil.returnDateSort);
                break;
            }
            case "duration": {
                roundTrips.sort(RoundTripsUtil.durationSort);
                break;
            }
            default: {
                roundTrips.sort(RoundTripsUtil.priceSort);
                break;
            }
        }

        return new RoundTripsView(roundTrips);
    }

}
