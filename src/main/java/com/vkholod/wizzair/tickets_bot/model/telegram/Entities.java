package com.vkholod.wizzair.tickets_bot.model.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Entities {

    @JsonProperty
    private String offset;

    @JsonProperty
    private String length;

    @JsonProperty
    private String type;

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
