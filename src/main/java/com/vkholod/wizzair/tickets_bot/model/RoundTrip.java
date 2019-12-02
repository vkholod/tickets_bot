package com.vkholod.wizzair.tickets_bot.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.math.BigDecimal;

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
    public BigDecimal totalPrice() {
        return outboundFlight.getPrice().getAmount().add(returnFlight.getPrice().getAmount());
    }

    @Override
    public String toString() {
        return String.format("\t%s\n\t%s\n\tTOTAL %s", outboundFlight, returnFlight, totalPrice());
    }
}
