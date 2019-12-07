package com.vkholod.wizzair.tickets_bot.model;

import java.util.Map;

public class ExchangeRates {
    private String date;

    private Map<String, String> rates;

    private String base;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, String> getRates() {
        return rates;
    }

    public void setRates(Map<String, String> rates) {
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    @Override
    public String toString() {
        return "ClassPojo [date = " + date + ", rates = " + rates + ", base = " + base + "]";
    }
}
