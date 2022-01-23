package com.example.rebornx30rbrespawntime.model.view;

import java.time.LocalDateTime;

public class RaidBossViewModel {
    private Long id;
    private String name;
    private Integer level;
    private boolean isAlive;
    private String respawnStart;
    private String respawnEnd;
    private String timeOfDeath;

    public RaidBossViewModel() {
    }

    public Long getId() {
        return id;
    }

    public RaidBossViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RaidBossViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public RaidBossViewModel setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public RaidBossViewModel setAlive(boolean alive) {
        isAlive = alive;
        return this;
    }

    public String getRespawnStart() {
        return respawnStart;
    }

    public RaidBossViewModel setRespawnStart(String respawnStart) {
        this.respawnStart = respawnStart;
        return this;
    }

    public String getRespawnEnd() {
        return respawnEnd;
    }

    public RaidBossViewModel setRespawnEnd(String respawnEnd) {
        this.respawnEnd = respawnEnd;
        return this;
    }

    public String getTimeOfDeath() {
        return timeOfDeath;
    }

    public RaidBossViewModel setTimeOfDeath(String timeOfDeath) {
        this.timeOfDeath = timeOfDeath;
        return this;
    }
}
