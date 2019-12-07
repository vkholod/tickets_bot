package com.vkholod.wizzair.tickets_bot.view;

import com.vkholod.wizzair.tickets_bot.model.RoundTrip;
import io.dropwizard.views.View;

import java.util.List;

public class RoundTripsView extends View {

    private final List<RoundTrip> roundTrips;

    public RoundTripsView(List<RoundTrip> roundTrips) {
        super("roundTrips.ftl");
        this.roundTrips = roundTrips;
    }

    public List<RoundTrip> getRoundTrips() {
        return roundTrips;
    }
}
