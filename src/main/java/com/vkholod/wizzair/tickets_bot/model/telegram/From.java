package com.vkholod.wizzair.tickets_bot.model.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

public class From {

    @JsonProperty("language_code")
    private String languageCode;

    @JsonProperty
    private String lastName;

    @JsonProperty
    private String id;

    @JsonProperty("is_bot")
    private String isBot;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty
    private String username;

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsBot() {
        return isBot;
    }

    public void setIsBot(String isBot) {
        this.isBot = isBot;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
