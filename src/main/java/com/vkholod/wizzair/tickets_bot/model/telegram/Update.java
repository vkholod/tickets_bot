package com.vkholod.wizzair.tickets_bot.model.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Update {

    @JsonProperty
    private List<Result> result;

    @JsonProperty
    private String ok;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }
}
