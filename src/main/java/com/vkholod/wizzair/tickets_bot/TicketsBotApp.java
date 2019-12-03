package com.vkholod.wizzair.tickets_bot;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.vkholod.wizzair.tickets_bot.dao.RedisStorage;
import com.vkholod.wizzair.tickets_bot.dao.WizzairTimetableClient;
import com.vkholod.wizzair.tickets_bot.resources.RoundTripResource;
import com.vkholod.wizzair.tickets_bot.service.TimetableService;
import com.vkholod.wizzair.tickets_bot.telegram.VovaTicketsBot;
import com.vkholod.wizzair.tickets_bot.util.TimetableObserver;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.concurrent.TimeUnit;

public class TicketsBotApp extends Application<TicketsBotConfig> {

    public static void main(String[] args) throws Exception {
        new TicketsBotApp().run(args);
    }

    @Override
    public void run(TicketsBotConfig config, Environment environment) throws Exception {

        OkHttpClient httpClient = httpClient(config);
        VovaTicketsBot bot = new VovaTicketsBot(httpClient, config.getTelegramBotToken(), config.getTelegramChatId());
        TimetableObserver timetableObserver = new TimetableObserver(bot);

        RedisStorage redisStorage = new RedisStorage(config.getRedisUri(), config.getRedisPoolConfig());

        WizzairTimetableClient timetableClient = new WizzairTimetableClient(mapper(), httpClient);

        TimetableService timetableService = new TimetableService(timetableClient, redisStorage, timetableObserver);

        environment.jersey().register(new RoundTripResource(timetableService));
    }

    @Override
    public String getName() {
        return "vova-tickets-bot";
    }

    @Override
    public void initialize(Bootstrap<TicketsBotConfig> bootstrap) {
    }

    private OkHttpClient httpClient(TicketsBotConfig config) {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(config.getOkHttpClientConnectTimeout(), TimeUnit.SECONDS);
        client.setReadTimeout(config.getOkHttpClientReadTimeout(), TimeUnit.SECONDS);
        return client;
    }

    private ObjectMapper mapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

}
