package com.vkholod.wizzair.tickets_bot.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vkholod.wizzair.tickets_bot.util.FlightDateDeserializer;
import com.vkholod.wizzair.tickets_bot.util.FlightDateSerializer;
import com.vkholod.wizzair.tickets_bot.util.FlightDateTimeDeserializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.vkholod.wizzair.tickets_bot.util.Const.DATE_FORMATTER;
import static com.vkholod.wizzair.tickets_bot.util.Const.DATE_TIME_HR_FORMATTER;

public class Flight {

    @JsonProperty
    private String departureStation;

    @JsonProperty
    private String arrivalStation;

    @JsonProperty
    @JsonSerialize(using = FlightDateSerializer.class)
    @JsonDeserialize(using = FlightDateDeserializer.class)
    private LocalDate from;

    @JsonProperty
    @JsonSerialize(using = FlightDateSerializer.class)
    @JsonDeserialize(using = FlightDateDeserializer.class)
    private LocalDate to;

    @JsonProperty
    private Price price;

    @JsonProperty
    @JsonDeserialize(using = FlightDateTimeDeserializer.class)
    private List<LocalDateTime> departureDates;

    public Flight() {
    }

    public Flight(String departureStation, String arrivalStation, LocalDate from, LocalDate to) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.from = from;
        this.to = to;
    }

    public Flight(String departureStation, String arrivalStation, LocalDate from, LocalDate to, Price price, List<LocalDateTime> departureDates) {
        this(departureStation, arrivalStation, from, to);
        this.price = price;
        this.departureDates = departureDates;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public List<LocalDateTime> getDepartureDates() {
        return departureDates;
    }

    public void setDepartureDates(List<LocalDateTime> departureDates) {
        this.departureDates = departureDates;
    }

    public LocalDateTime departureDateTime() {
        return departureDates.stream().sorted().findFirst().get();
    }

    public boolean isBeforeOrEqual(Flight otherFlight) {
        return this.departureDateTime().isBefore(otherFlight.departureDateTime()) || this.departureDateTime().isEqual(otherFlight.departureDateTime());
    }

    public boolean isEqualOrAfter(Flight otherFlight) {
        return this.departureDateTime().isEqual(otherFlight.departureDateTime()) || this.departureDateTime().isAfter(otherFlight.departureDateTime());
    }

    public String generateRedisKey() {
        return String.join("_", departureStation, arrivalStation, DATE_FORMATTER.format(from), DATE_FORMATTER.format(to));
    }

    @Override
    public String toString() {
        return String.format("%s->%s %s, %s",
                departureStation, arrivalStation,
                DATE_TIME_HR_FORMATTER.format(departureDateTime()), price.getAmount());
    }
}
