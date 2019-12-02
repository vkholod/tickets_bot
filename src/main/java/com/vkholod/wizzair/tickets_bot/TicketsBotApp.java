package com.vkholod.wizzair.tickets_bot;

import com.vkholod.wizzair.tickets_bot.resources.TimetableResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TicketsBotApp extends Application<TicketsBotConfig> {

    public static void main(String[] args) throws Exception {
        new TicketsBotApp().run(args);
    }

    @Override
    public void run(TicketsBotConfig ticketsBotConfig, Environment environment) throws Exception {
        TimetableResource resource = new TimetableResource();

        environment.jersey().register(resource);
    }

    @Override
    public String getName() {
        return "vova-tickets-bot";
    }

    @Override
    public void initialize(Bootstrap<TicketsBotConfig> bootstrap) {
    }
}
