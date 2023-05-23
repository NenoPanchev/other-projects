package com.example.rebornx30rbrespawntime.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RaidBossUpdateDto {
    @Expose
    private String status;
    @Expose
    @JsonProperty("id")
    private String rebornId;
    @Expose
    private String date;

    public RaidBossUpdateDto() {
    }

    public String getStatus() {
        return status;
    }

    public RaidBossUpdateDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getRebornId() {
        return rebornId;
    }

    public RaidBossUpdateDto setRebornId(String rebornId) {
        this.rebornId = rebornId;
        return this;
    }

    public String getDate() {
        return date;
    }

    public RaidBossUpdateDto setDate(String date) {
        this.date = date;
        return this;
    }
}
