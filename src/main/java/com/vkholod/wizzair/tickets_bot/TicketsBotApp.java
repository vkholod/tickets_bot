package com.vkholod.wizzair.tickets_bot;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.vkholod.wizzair.tickets_bot.dao.RedisStorage;
import com.vkholod.wizzair.tickets_bot.dao.WizzairTimetableClient;
import com.vkholod.wizzair.tickets_bot.resources.RoundTripResource;
import com.vkholod.wizzair.tickets_bot.service.TimetableService;
import com.vkholod.wizzair.tickets_bot.telegram.*;
import com.vkholod.wizzair.tickets_bot.util.QuartzUtils;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.vkholod.wizzair.tickets_bot.util.Const.*;

public class TicketsBotApp extends Application<TicketsBotConfig> {

    public static void main(String[] args) throws Exception {
        new TicketsBotApp().run(args);
    }

    @Override
    public void run(TicketsBotConfig config, Environment environment) throws SchedulerException {
        OkHttpClient httpClient = httpClient(config);
        ObjectMapper mapper = mapper();

        VovaTicketsBot bot = new VovaTicketsBot(httpClient, config.getTelegramBotToken(), config.getTelegramChatId());

        WizzairTimetableClient timetableClient = new WizzairTimetableClient(mapper, httpClient);
        TimetableService timetableService = new TimetableService(timetableClient);

        RedisStorage redisStorage = new RedisStorage(config.getRedisUri(), config.getRedisPoolConfig(), mapper);

        Scheduler scheduler = initScheduler(bot, redisStorage, timetableService, mapper);

        environment.jersey().register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(scheduler).to(Scheduler.class);
            }
        });

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

    private Scheduler initScheduler(
            VovaTicketsBot bot,
            RedisStorage storage,
            TimetableService service,
            ObjectMapper mapper
    ) throws SchedulerException {

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();

        List<TelegramMessageProcessor> processors = List.of(
                new StatusProcessor(bot),
                new ToggleTimetableCheckProcessor(bot, scheduler),
                new TimetableProcessor(bot, service, storage)
        );

        JobDetail timetableCheckJob = QuartzUtils.createTimetableCheckJob(bot, storage, service, processors);
        JobDetail telegramJob = QuartzUtils.createTelegramJob(bot, storage, mapper, processors);

        Trigger telegramTrigger = QuartzUtils.preparePermanentTrigger(TELEGRAM_JOB_NAME, TELEGRAM_TRIGGER_NAME, 5);
        Trigger timetableCheckTrigger = QuartzUtils.preparePermanentTrigger(TIMETABLE_CHECK_JOB_NAME, TIMETABLE_CHEC_TRIGGER_NAME, 30);

        scheduler.scheduleJob(telegramJob, telegramTrigger);
        scheduler.scheduleJob(timetableCheckJob, timetableCheckTrigger);

        scheduler.pauseJob(JobKey.jobKey(TIMETABLE_CHECK_JOB_NAME));

        scheduler.start();

        return scheduler;
    }

}
