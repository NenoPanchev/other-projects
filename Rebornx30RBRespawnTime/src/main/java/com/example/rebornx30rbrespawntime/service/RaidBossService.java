package com.example.rebornx30rbrespawntime.service;

import com.example.rebornx30rbrespawntime.model.entity.RaidBoss;

import java.time.LocalDateTime;
import java.util.List;

public interface RaidBossService {
    void seedRaidBoss(RaidBoss raidBoss);
    List<RaidBoss> getAllRaidBosses();

    void updateInfo(List<RaidBoss> raidBosses);

    void updateTimeOfDeath(Long id, LocalDateTime timeOfDeath);
}
