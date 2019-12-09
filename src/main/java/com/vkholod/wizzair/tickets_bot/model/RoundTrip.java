package com.vkholod.wizzair.tickets_bot.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.math.BigDecimal;
import java.time.Duration;

public class RoundTrip {

    private Flight outboundFlight;
    private Flight returnFlight;

    public RoundTrip(Flight outboundFlight, Flight returnFlight) {
        this.outboundFlight = outboundFlight;
        this.returnFlight = returnFlight;
    }

    public Flight getOutboundFlight() {
        return outboundFlight;
    }

    public void setOutboundFlight(Flight outboundFlight) {
        this.outboundFlight = outboundFlight;
    }

    public Flight getReturnFlight() {
        return returnFlight;
    }

    public void setReturnFlight(Flight returnFlight) {
        this.returnFlight = returnFlight;
    }

    @JsonGetter
    public Price getTotalPrice() {
        BigDecimal origPrice = outboundFlight.getPrice().getAmount().add(returnFlight.getPrice().getAmount());
        BigDecimal dollarsPrice = outboundFlight.getPrice().getDollars().add(returnFlight.getPrice().getDollars());

        return new Price(origPrice, outboundFlight.getPrice().getCurrencyCode(), dollarsPrice);
    }

    @JsonGetter
    public long getDuration() {
        return Duration.between(
                outboundFlight.departureDateTime(),
                returnFlight.departureDateTime()
        ).toDays() + 1;
    }

    @Override
    public String toString() {
        return String.format("\t%s\n\t%s\n\tTOTAL %s", outboundFlight, returnFlight, getTotalPrice());
    }
}
