package com.vkholod.wizzair.tickets_bot.service;

import com.vkholod.wizzair.tickets_bot.dao.ExchangeRatesClient;
import com.vkholod.wizzair.tickets_bot.dao.WizzairTimetableClient;
import com.vkholod.wizzair.tickets_bot.model.Timetable;
import com.vkholod.wizzair.tickets_bot.model.TimetableRequestDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

public class TimetableService {

    private WizzairTimetableClient client;
    private ExchangeRatesClient ratesClient;

    public TimetableService(WizzairTimetableClient client, ExchangeRatesClient ratesClient) {
        this.client = client;
        this.ratesClient = ratesClient;
    }

    public Timetable getTimetable(TimetableRequestDto timetableRequestDto) throws IOException {
        Timetable timetable = client.fetchTimetable(timetableRequestDto);

        if (!timetable.getOutboundFlights().isEmpty()) {
            BigDecimal exchangeRate = ratesClient.fetchUsdExchangeRate(timetable.getOutboundFlights().get(0).getPrice().getCurrencyCode());
            Stream.concat(
                    timetable.getOutboundFlights().stream(),
                    timetable.getReturnFlights().stream()
            ).forEach(flight -> flight.getPrice().setDollars(flight.getPrice().getAmount().multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP)));
        }

        return timetable;
    }
}
