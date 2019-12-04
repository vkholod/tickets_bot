package com.vkholod.wizzair.tickets_bot.model.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Message {

    @JsonProperty
    private String date;

    @JsonProperty
    private List<Entities> entities;

    @JsonProperty
    private Chat chat;

    @JsonProperty("message_id")
    private String messageId;

    @JsonProperty
    private From from;

    @JsonProperty
    private String text;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Entities> getEntities() {
        return entities;
    }

    public void setEntities(List<Entities> entities) {
        this.entities = entities;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
