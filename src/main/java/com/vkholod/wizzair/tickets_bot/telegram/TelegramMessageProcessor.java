package com.vkholod.wizzair.tickets_bot.telegram;

import com.vkholod.wizzair.tickets_bot.model.telegram.Message;
import com.vkholod.wizzair.tickets_bot.model.telegram.Result;
import com.vkholod.wizzair.tickets_bot.model.telegram.Update;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public abstract class TelegramMessageProcessor {

    protected VovaTicketsBot bot;

    public TelegramMessageProcessor(VovaTicketsBot bot) {
        this.bot = bot;
    }

    public final void process(Update update) {
        extractProcessableMessages(update).forEach(this::internalProcess);
    }

    protected abstract void internalProcess(String message);

    protected abstract String keyWord();

    private List<String> extractProcessableMessages(Update update) {
        return update.getResult().stream()
                .map(result -> result.getMessage().getText())
                .filter(text -> StringUtils.startsWithIgnoreCase(text, keyWord()))
                .collect(Collectors.toList());
    }

}
