package com.vkholod.wizzair.tickets_bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

public class Price {

    @JsonProperty
    private BigDecimal amount;

    @JsonProperty
    private String currencyCode;

    @JsonProperty
    private BigDecimal dollars;

    public Price() {
    }

    public Price(BigDecimal amount, String currencyCode) {
        this.amount = amount;
        this.currencyCode = currencyCode;
    }

    public Price(BigDecimal amount, String currencyCode, BigDecimal dollars) {
        this(amount, currencyCode);
        this.dollars = dollars;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getDollars() {
        return dollars;
    }

    public void setDollars(BigDecimal dollars) {
        this.dollars = dollars;
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
