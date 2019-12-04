package com.vkholod.wizzair.tickets_bot;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.squareup.okhttp.OkHttpClient;
import com.vkholod.wizzair.tickets_bot.dao.RedisStorage;
import com.vkholod.wizzair.tickets_bot.dao.WizzairTimetableClient;
import com.vkholod.wizzair.tickets_bot.job.TelegramJob;
import com.vkholod.wizzair.tickets_bot.model.telegram.Update;
import com.vkholod.wizzair.tickets_bot.resources.RoundTripResource;
import com.vkholod.wizzair.tickets_bot.service.TimetableService;
import com.vkholod.wizzair.tickets_bot.telegram.VovaTicketsBot;
import com.vkholod.wizzair.tickets_bot.job.TimetableCheckJob;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

public class TicketsBotApp extends Application<TicketsBotConfig> {

    public static void main(String[] args) throws Exception {
        new TicketsBotApp().run(args);
    }

    @Override
    public void run(TicketsBotConfig config, Environment environment) {
        OkHttpClient httpClient = httpClient(config);
        ObjectMapper mapper = mapper();

        VovaTicketsBot bot = new VovaTicketsBot(httpClient, config.getTelegramBotToken(), config.getTelegramChatId());

        WizzairTimetableClient timetableClient = new WizzairTimetableClient(mapper, httpClient);
        TimetableService timetableService = new TimetableService(timetableClient);

        RedisStorage redisStorage = new RedisStorage(config.getRedisUri(), config.getRedisPoolConfig(), mapper);

        setUpScheduler(bot, timetableService, redisStorage, mapper);

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

    private void setUpScheduler(VovaTicketsBot bot, TimetableService timetableService, RedisStorage storage, ObjectMapper mapper) {
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("bot", bot);
            jobDataMap.put("timetableService", timetableService);
            jobDataMap.put("storage", storage);
            jobDataMap.put("reader", mapper.readerFor(Update.class));

            JobDetail timeTableCheckJob = JobBuilder.newJob(TimetableCheckJob.class)
                    .withIdentity("timeTableCheckJob", "group1")
                    .usingJobData(jobDataMap)
                    .build();

            Trigger timeTableCheckTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("timeTableCheckTrigger", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInHours(1)
                            .repeatForever()
                    )
                    .build();

            JobDetail telegramJob = JobBuilder.newJob(TelegramJob.class)
                    .withIdentity("telegramJob", "group1")
                    .usingJobData(jobDataMap)
                    .build();

            Trigger telegramTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("telegramTrigger", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(5)
                            .repeatForever()
                    )
                    .build();

            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();

            scheduler.scheduleJob(timeTableCheckJob, timeTableCheckTrigger);
            scheduler.scheduleJob(telegramJob, telegramTrigger);

            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
