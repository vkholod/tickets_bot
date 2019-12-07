package com.vkholod.wizzair.tickets_bot.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.squareup.okhttp.*;
import com.vkholod.wizzair.tickets_bot.model.ExchangeRates;

import java.io.IOException;
import java.math.BigDecimal;

public class ExchangeRatesClient {

    private OkHttpClient client;
    private ObjectReader reader;

    private static final HttpUrl BASE_URL = buildBaseUrl();

    public ExchangeRatesClient(ObjectMapper mapper, OkHttpClient client) {
        this.client = client;
        this.reader = mapper.readerFor(ExchangeRates.class);
    }

    public BigDecimal fetchUsdExchangeRate(String base) throws IOException {
        HttpUrl url = BASE_URL.newBuilder()
                .addQueryParameter("base", base)
                .addQueryParameter("symbols", "USD")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        String str = response.body().string();

        ExchangeRates rate = reader.readValue(str);

        return new BigDecimal(rate.getRates().get("USD"));
    }

    private static HttpUrl buildBaseUrl() {
        return new HttpUrl.Builder()
                .scheme("https")
                .host("api.exchangeratesapi.io")
                .addPathSegment("latest")
                .build();
    }
}
