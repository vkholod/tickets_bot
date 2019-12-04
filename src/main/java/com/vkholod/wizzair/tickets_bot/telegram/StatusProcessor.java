package com.vkholod.wizzair.tickets_bot.telegram;

import com.vkholod.wizzair.tickets_bot.model.telegram.Update;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class StatusProcessor extends TelegramMessageProcessor {

    public StatusProcessor(VovaTicketsBot bot) {
        super(bot);
    }

    @Override
    public void internalProcess(String message) {
        try {
            bot.sendMessage("I am alive!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String keyWord() {
        return "/status";
    }
}
