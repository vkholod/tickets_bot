package com.vkholod.wizzair.tickets_bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimetableRequestDto {

    private List<Flight> flightList;

    @JsonProperty
    private String priceType;

    @JsonProperty
    private int adultCount;

    @JsonProperty
    private int childCount;

    @JsonProperty
    private int infantCount;

    public TimetableRequestDto(List<Flight> flightList, int adultCount) {
        this(flightList, "regular", adultCount, 0, 0);
    }

    public TimetableRequestDto(List<Flight> flightList, String priceType, int adultCount, int childCount, int infantCount) {
        this.flightList = flightList;
        this.priceType = priceType;
        this.adultCount = adultCount;
        this.childCount = childCount;
        this.infantCount = infantCount;
    }

    public static TimetableRequestDto create(
            String departureStation, String arrivalStation, LocalDate from, LocalDate to, int adultCount
    ) {
        Flight outboundFlight = new Flight(departureStation, arrivalStation, from, to);
        Flight returnFlight = new Flight(arrivalStation, departureStation, from, to);

        return new TimetableRequestDto(Stream.of(outboundFlight, returnFlight).collect(Collectors.toList()), adultCount);
    }

    public static TimetableRequestDto defaultDto() {
        return TimetableRequestDto.create(
                "SCV", "CIA",
                LocalDate.of(2020, 5, 11), LocalDate.of(2020, 5, 31),
                2
        );
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<Flight> flightList) {
        this.flightList = flightList;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public int getInfantCount() {
        return infantCount;
    }

    public void setInfantCount(int infantCount) {
        this.infantCount = infantCount;
    }

    public String generateRedisKey() {
        String flightsKey = flightList.stream()
                .map(Flight::generateRedisKey)
                .collect(Collectors.joining("_"));

        return String.join("_", flightsKey, String.valueOf(adultCount));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
