package com.vkholod.wizzair.tickets_bot;

import io.dropwizard.Configuration;

public class TicketsBotConfig extends Configuration {
    private String tmpVar;

    public String getTmpVar() {
        return tmpVar;
    }

    public void setTmpVar(String tmpVar) {
        this.tmpVar = tmpVar;
    }
}
