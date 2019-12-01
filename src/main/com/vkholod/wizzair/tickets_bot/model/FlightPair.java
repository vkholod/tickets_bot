package com.vkholod.wizzair.tickets_bot.model;

import com.vkholod.wizzair.tickets_bot.util.Const;

import java.math.BigDecimal;

import static com.vkholod.wizzair.tickets_bot.util.Const.DATE_TIME_FORMATTER;

public class FlightPair {

    private Flight outboundFlight;
    private Flight returnFlight;

    public FlightPair(Flight outboundFlight, Flight returnFlight) {
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

    public BigDecimal totalPrice() {
        return outboundFlight.getPrice().getAmount().add(returnFlight.getPrice().getAmount());
    }

    @Override
    public String toString() {
        return String.format("\t%s\n\t%s\n\tTOTAL %s", outboundFlight, returnFlight, totalPrice());
    }
}
