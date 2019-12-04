package com.vkholod.wizzair.tickets_bot.model.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Chat {

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty
    private String id;

    @JsonProperty
    private String type;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty
    private String username;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
