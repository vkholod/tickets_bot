package com.vkholod.wizzair.tickets_bot.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class Timetable {
    private List<Flight> outboundFlights;
    private List<Flight> returnFlights;

    public Timetable() {
    }

    public Timetable(List<Flight> outboundFlights, List<Flight> returnFlights) {
        this.outboundFlights = outboundFlights;
        this.returnFlights = returnFlights;
    }

    public List<Flight> getOutboundFlights() {
        return outboundFlights;
    }

    public void setOutboundFlights(List<Flight> outboundFlights) {
        this.outboundFlights = outboundFlights;
    }

    public List<Flight> getReturnFlights() {
        return returnFlights;
    }

    public void setReturnFlights(List<Flight> returnFlights) {
        this.returnFlights = returnFlights;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }
}
